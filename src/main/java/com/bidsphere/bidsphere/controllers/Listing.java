package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.*;
import com.bidsphere.bidsphere.entities.*;
import com.bidsphere.bidsphere.payloads.CatalogRequest;
import com.bidsphere.bidsphere.payloads.CatalogResponse;
import com.bidsphere.bidsphere.payloads.HomepageResponse;
import com.bidsphere.bidsphere.repositories.*;
import com.bidsphere.bidsphere.services.EthereumService;
import com.bidsphere.bidsphere.types.ListingStatus;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Null;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.net.URI;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/listing")
public class Listing extends SessionizedController {

    private final ListingsRepository listingsRepository;
    private final ListingImagesRepository listingImagesRepository;
    private final BidsRepository bidsRepository;
    private final UsersRepository usersRepository;
    private final ProductsRepository productsRepository;
    private final TransactionsRepository transactionsRepository;
    private final EthereumService ethereumService;

    public Listing(
            ListingsRepository listingsRepository,
            ListingImagesRepository listingImagesRepository,
            BidsRepository bidsRepository,
            UsersRepository usersRepository,
            ProductsRepository productsRepository,
            TransactionsRepository transactionsRepository,
            EthereumService ethereumService
    ) {
        this.listingsRepository = listingsRepository;
        this.listingImagesRepository = listingImagesRepository;
        this.bidsRepository = bidsRepository;
        this.usersRepository = usersRepository;
        this.productsRepository = productsRepository;
        this.transactionsRepository = transactionsRepository;
        this.ethereumService = ethereumService;
    }

    @PatchMapping("/finish/{listingId}")
    public ResponseEntity<Null> finishListing(@PathVariable UUID listingId) throws Exception {
        Sessions session = this.getSession();
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Listings> listingQuery = this.listingsRepository.findById(listingId);
        if (listingQuery.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Listings listing = listingQuery.get();
        if (!listing.getSellerId().equals(session.getUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Bids> bidQuery = this.bidsRepository.findLatestBidByListingId(listing.getListingId());
        if (bidQuery.isEmpty()) {
            listing.setStatus(ListingStatus.UNLISTED);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        System.out.println("Ending listing (" + listingId + ")");

        Bids bid = bidQuery.get();
        TransactionReceipt receipt = this.ethereumService.contract
                .endListing(EthereumService.uuidToBytes(listing.getListingId()))
                .send();


        System.out.println("Ended listing (" + listingId + ") with transaction [" + receipt.getTransactionHash() + "]");

        Transactions transaction = new Transactions(listing, bid, receipt.getTransactionHash());
        this.transactionsRepository.save(transaction);

        listing.setSellerId(bid.getUserId());
        listing.setStatus(ListingStatus.UNLISTED);
        this.listingsRepository.save(listing);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<ListingDTO> getListing(@PathVariable UUID listingId) {
        Optional<Listings> listingQuery = this.listingsRepository.findById(listingId);
        if (listingQuery.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Listings listing = listingQuery.get();
        Optional<Products> productEntry = this.productsRepository.findByProductId(listing.getProductId());

        if (productEntry.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ArrayList<UUID> listingImages = this.getListingImageIds(listingId);
        ProductDTO productDTO = new ProductDTO(productEntry.get());

        return ResponseEntity.ok(new ListingDTO(listing, productDTO, listingImages, this.getLatestBid(listingId), this.getLatestTransaction(listingId)));
    }

    @DeleteMapping("/{listingId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listing deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - session is missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Listing not found")
    })
    public ResponseEntity<UUID> deleteListing(@PathVariable UUID listingId) {
        Sessions session = this.getSession();
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Listings> listingQuery = this.listingsRepository.findById(listingId);
        if (listingQuery.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Users> listingSeller = this.usersRepository.findById(session.getUserId());

        if (listingSeller.isEmpty() || listingSeller.get().getId() != session.getUserId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        this.listingsRepository.delete(listingQuery.get());

        return ResponseEntity.ok(listingId);
    }

    @PostMapping("/catalog")
    public ResponseEntity<CatalogResponse> getCatalog(@RequestBody CatalogRequest request) {
        List<ListingDTO> listingDTOs = this.getListings(request.getPage());

        return ResponseEntity.ok(new CatalogResponse(listingDTOs));
    }

    @GetMapping("/homepage")
    public ResponseEntity<HomepageResponse> getHomepageListings() {
        HomepageResponse homepageResponse = new HomepageResponse(
                this.getNewListings(),
                this.getTrendingListings(),
                this.getPopularListings()
        );

        return ResponseEntity.ok(homepageResponse);
    }

    private List<ListingDTO> getNewListings() {
        Pageable pageable = PageRequest.of(0, 10);
        ArrayList<Listings> listingEntries = this.listingsRepository.findLatestListings(pageable);

        List<ListingDTO> listingDTOs = new ArrayList<>();

        for (Listings listing : listingEntries) {
            UUID listingId = listing.getListingId();
            ArrayList<UUID> listingImageIds = this.getListingImageIds(listingId);
            Optional<Products> productEntry = this.productsRepository.findByProductId(listing.getProductId());

            if (productEntry.isEmpty()) {
                continue;
            }

            ProductDTO productDTO = new ProductDTO(productEntry.get());

            listingDTOs.add(new ListingDTO(listing, productDTO, listingImageIds, this.getLatestBid(listingId), this.getLatestTransaction(listingId)));
        }

        return listingDTOs;
    }

    private List<ListingDTO> getTrendingListings() {
        return this.getListings(0);
    }

    private List<ListingDTO> getPopularListings() {
        return this.getListings(0);
    }

    private List<ListingDTO> getListings(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        List<Listings> listingEntries = this.listingsRepository
                .findAllByStatus(ListingStatus.ACTIVE, pageable)
                .stream()
                .toList();

        List<ListingDTO> listingDTOs = new ArrayList<>();

        for (Listings listing : listingEntries) {
            UUID listingId = listing.getListingId();
            ArrayList<UUID> listingImageIds = this.getListingImageIds(listingId);
            Optional<Products> productEntry = this.productsRepository.findByProductId(listing.getProductId());

            if (productEntry.isEmpty()) {
                continue;
            }

            ProductDTO productDTO = new ProductDTO(productEntry.get());

            listingDTOs.add(new ListingDTO(listing, productDTO, listingImageIds, this.getLatestBid(listingId), this.getLatestTransaction(listingId)));
        }

        return listingDTOs;
    }

    private BidDTO getLatestBid(UUID listingId) {
        Optional<Bids> bidsQuery = this.bidsRepository.findLatestBidByListingId(listingId);

        return bidsQuery.map(BidDTO::new).orElse(null);
    }

    private TransactionDTO getLatestTransaction(UUID listingId) {
        Optional<Transactions> transactionEntry = this.transactionsRepository.findByListingId(listingId);
        return transactionEntry.map(TransactionDTO::new).orElse(null);
    }

    private ArrayList<UUID> getListingImageIds(UUID listingId) {
        List<ListingImages> listingImages = this.listingImagesRepository.findAllByListingId(listingId);
        ArrayList<UUID> listingImageIds = new ArrayList<>();

        for (ListingImages listingImage : listingImages) {
            listingImageIds.add(listingImage.getImageId());
        }

        return listingImageIds;
    }
}
