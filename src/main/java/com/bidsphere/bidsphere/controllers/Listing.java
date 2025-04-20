package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.BidDTO;
import com.bidsphere.bidsphere.dtos.ListingDTO;
import com.bidsphere.bidsphere.entities.*;
import com.bidsphere.bidsphere.payloads.CatalogRequest;
import com.bidsphere.bidsphere.payloads.CatalogResponse;
import com.bidsphere.bidsphere.payloads.HomepageResponse;
import com.bidsphere.bidsphere.repositories.BidsRepository;
import com.bidsphere.bidsphere.repositories.ListingImagesRepository;
import com.bidsphere.bidsphere.repositories.ListingsRepository;
import com.bidsphere.bidsphere.repositories.SellersRepository;
import com.bidsphere.bidsphere.types.ListingStatus;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Null;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/listing")
public class Listing extends SessionizedController {

    private final ListingsRepository listingsRepository;
    private final ListingImagesRepository listingImagesRepository;
    private final SellersRepository sellersRepository;
    private final BidsRepository bidsRepository;

    public Listing(
            ListingsRepository listingsRepository,
            ListingImagesRepository listingImagesRepository,
            SellersRepository sellersRepository,
            BidsRepository bidsRepository
    ) {
        this.listingsRepository = listingsRepository;
        this.listingImagesRepository = listingImagesRepository;
        this.sellersRepository = sellersRepository;
        this.bidsRepository = bidsRepository;
    }

    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Listing created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid product authenticity - cannot create the listing")
    })
    public ResponseEntity<String> createListing(@RequestBody ListingDTO listingDTO) {
        Sessions session = this.getSession();
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!this.sellersRepository.existsById(listingDTO.getSellerId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UUID listingId = UUID.randomUUID();

        if (listingDTO.getAuthenticity() < 3) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Listings listings = new Listings(listingId, listingDTO);
        listings.setStartDate(new Date());
        listings.setStatus(ListingStatus.ACTIVE);

        this.listingsRepository.save(listings);

        ArrayList<ListingImages> images = new ArrayList<>();
        for (UUID listingImageId : listingDTO.getDisplayImageIds()) {
            images.add(new ListingImages(listingId, listingImageId));
        }

        this.listingImagesRepository.saveAll(images);

        return ResponseEntity.created(URI.create("/api/listing/" + listingId)).body(listingId.toString());
    }

    @PatchMapping("/finish/{listingId}")
    public ResponseEntity<Null> finishListing(@PathVariable UUID listingId) {
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

        listing.setStatus(ListingStatus.CLOSED);
        this.listingsRepository.save(listing);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/terminate/{listingId}")
    public ResponseEntity<String> terminateListing(@PathVariable UUID listingId) {
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

        listing.setStatus(ListingStatus.TERMINATED);
        this.listingsRepository.save(listing);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<ListingDTO> getListing(@PathVariable UUID listingId) {
        Optional<Listings> listingQuery = this.listingsRepository.findById(listingId);
        if (listingQuery.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ArrayList<UUID> listingImages = this.getListingImageIds(listingId);

        return ResponseEntity.ok(new ListingDTO(listingQuery.get(), listingImages, this.getLatestBid(listingId)));
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

        Optional<Sellers> listingSeller = sellersRepository.findById(session.getUserId());

        if (listingSeller.isEmpty() || listingSeller.get().getUserId() != session.getUserId()) {
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
        return this.getListings(1);
    }

    private List<ListingDTO> getTrendingListings() {
        return this.getListings(1);
    }

    private List<ListingDTO> getPopularListings() {
        return this.getListings(1);
    }

    private List<ListingDTO> getListings(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        List<Listings> listingEntries = this.listingsRepository
                .findAllByStatus(ListingStatus.ACTIVE, pageable)
                .stream()
                .toList();

        List<ListingDTO> listingDTOs = new ArrayList<>();

        for (Listings listing : listingEntries) {
            ArrayList<UUID> listingImageIds = this.getListingImageIds(listing.getId());
            listingDTOs.add(new ListingDTO(listing, listingImageIds, this.getLatestBid(listing.getId())));
        }

        return listingDTOs;
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
}
