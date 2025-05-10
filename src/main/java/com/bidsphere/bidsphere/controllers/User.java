package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.*;
import com.bidsphere.bidsphere.entities.*;
import com.bidsphere.bidsphere.repositories.*;
import com.bidsphere.bidsphere.services.PasswordHandler;
import com.bidsphere.bidsphere.types.ListingStatus;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class User extends SessionizedController {

    private final UsersRepository usersRepository;
    private final BidsRepository bidsRepository;
    private final ListingsRepository listingsRepository;
    private final ListingImagesRepository listingImagesRepository;
    private final TransactionsRepository transactionsRepository;
    private final ProductsRepository productsRepository;
    private final CredentialsRepository credentialsRepository;

    private PasswordHandler passwordHandler;


    public User(
            UsersRepository usersRepository,
            BidsRepository bidsRepository,
            ListingsRepository listingsRepository,
            ListingImagesRepository listingImagesRepository,
            TransactionsRepository transactionsRepository,
            ProductsRepository productsRepository,
            CredentialsRepository credentialsRepository
    ) {
        this.usersRepository = usersRepository;
        this.bidsRepository = bidsRepository;
        this.listingsRepository = listingsRepository;
        this.listingImagesRepository = listingImagesRepository;
        this.transactionsRepository = transactionsRepository;
        this.productsRepository = productsRepository;
        this.credentialsRepository = credentialsRepository;
    }

    @Autowired
    public void setPasswordHandler(PasswordHandler passwordHandler) {
        this.passwordHandler = passwordHandler;
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateProfile(@RequestBody UserUpdateDTO userUpdateDTO) {
        Sessions session = this.getSession();
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UUID userId = session.getUserId();
        Optional<Users> userEntry = usersRepository.findById(userId);
        if (userEntry.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Credentials> credentialsEntry = this.credentialsRepository.findByUserId(userId);
        if (credentialsEntry.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Users user = userEntry.get();
        Credentials credentials = credentialsEntry.get();
        String newPasswordHash = this.passwordHandler.toHash(userUpdateDTO.getCurrentPassword());

        boolean shouldUpdateEmail = userUpdateDTO.getEmail() != null;
        boolean shouldUpdatePassword = userUpdateDTO.getCurrentPassword() != null;
        boolean shouldUpdateAvatar = userUpdateDTO.getAvatarId() != null;
        boolean shouldUpdateDeliveryLocation = userUpdateDTO.getDeliveryLocation() != null;
        boolean shouldApplyChanges = this.passwordHandler.hashMatches(credentials.getPasswordHash(), newPasswordHash);

        if (!shouldApplyChanges) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (shouldUpdateEmail) {
            credentials.setEmail(userUpdateDTO.getEmail());
        }

        if (shouldUpdatePassword) {
            credentials.setPasswordHash(newPasswordHash);
        }

        if (shouldUpdateAvatar) {
            user.setAvatarId(userUpdateDTO.getAvatarId());
        }

        if (shouldUpdateDeliveryLocation) {
            user.setDeliveryLocation(userUpdateDTO.getDeliveryLocation());
        }

        if (shouldUpdateAvatar || shouldUpdateDeliveryLocation) {
            this.usersRepository.save(user);
        }

        if (shouldUpdateEmail || shouldUpdatePassword) {
            this.credentialsRepository.save(credentials);
        }

        return ResponseEntity.ok(true);
    }

    @GetMapping("/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found and returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId) {
        Optional<Users> userQuery = this.usersRepository.findById(userId);

        if (userQuery.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new UserDTO(userQuery.get()));
    }

    @GetMapping("/history/{page}")
    public ResponseEntity<ArrayList<ListingDTO>> getHistory(@PathVariable int page) {
        Sessions session = this.getSession();
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Pageable pageable = PageRequest.of(page, 10);
        ArrayList<Listings> listingEntries = this.listingsRepository
                .findAllBySellerIdAndStatusGreaterThan(session.getUserId(), ListingStatus.ACTIVE, pageable);

        ArrayList<ListingDTO> listingDTOs = new ArrayList<>();
        ArrayList<Transactions> transactionEntries = this.transactionsRepository.findAllByPreviousOwnerId(session.getUserId());

        for (Transactions transaction : transactionEntries) {
            Optional<Listings> listingQuery = this.listingsRepository.findById(transaction.getListingId());

            if (listingQuery.isEmpty()) {
                continue;
            }

            listingDTOs.add(this.getListingDTO(listingQuery.get()));
        }

        for (Listings listing : listingEntries) {
            listingDTOs.add(this.getListingDTO(listing));
        }

        return ResponseEntity.ok(listingDTOs);
    }

    @GetMapping("/inventory/{page}")
    public ResponseEntity<ArrayList<ListingDTO>> getOwnedListings(@PathVariable int page) {
        Sessions session = this.getSession();
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Pageable pageable = PageRequest.of(page, 10);
        ArrayList<Listings> listingEntries = this.listingsRepository
                .findAllBySellerIdAndStatusLessThanEqual(session.getUserId(), ListingStatus.ACTIVE, pageable);

        ArrayList<ListingDTO> listingDTOs = new ArrayList<>();
        for (Listings listing : listingEntries) {
            listingDTOs.add(this.getListingDTO(listing));
        }

        return ResponseEntity.ok(listingDTOs);
    }

    private BidDTO getLatestBid(UUID listingId) {
        Optional<Bids> bidsQuery = this.bidsRepository.findLatestBidByListingId(listingId);
        return bidsQuery.map(BidDTO::new).orElse(null);

    }

    private ArrayList<UUID> getListingImageIds(UUID listingId) {
        List<ListingImages> listingImages = this.listingImagesRepository.findAllByListingId(listingId);
        ArrayList<UUID> listingImageIds = new ArrayList<>();

        for (ListingImages listingImage : listingImages) {
            listingImageIds.add(listingImage.getImageId());
        }

        return listingImageIds;
    }

    private TransactionDTO getLatestTransaction(UUID listingId) {
        Optional<Transactions> transactionEntry = this.transactionsRepository.findByListingId(listingId);
        return transactionEntry.map(TransactionDTO::new).orElse(null);
    }

    ListingDTO getListingDTO(Listings listing) {
        Optional<Products> productEntry = this.productsRepository.findByProductId(listing.getProductId());
        if (productEntry.isEmpty()) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO(productEntry.get());

        return new ListingDTO(
                listing,
                productDTO,
                this.getListingImageIds(listing.getListingId()),
                this.getLatestBid(listing.getListingId()),
                this.getLatestTransaction(listing.getListingId())
        );
    }
}
