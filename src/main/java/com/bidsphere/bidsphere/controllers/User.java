package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.BidDTO;
import com.bidsphere.bidsphere.dtos.ListingDTO;
import com.bidsphere.bidsphere.dtos.UserDTO;
import com.bidsphere.bidsphere.entities.*;
import com.bidsphere.bidsphere.repositories.*;
import com.bidsphere.bidsphere.types.ListingStatus;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    public User(
            UsersRepository usersRepository,
            BidsRepository bidsRepository,
            ListingsRepository listingsRepository,
            ListingImagesRepository listingImagesRepository,
            TransactionsRepository transactionsRepository
    ) {
        this.usersRepository = usersRepository;
        this.bidsRepository = bidsRepository;
        this.listingsRepository = listingsRepository;
        this.listingImagesRepository = listingImagesRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateProfile() {
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

    @GetMapping("/owned/{page}")
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

    ListingDTO getListingDTO(Listings listing) {
        return new ListingDTO(
                listing,
                this.getListingImageIds(listing.getId()),
                this.getLatestBid(listing.getId())
        );
    }
}
