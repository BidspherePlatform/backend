package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.BidDTO;
import com.bidsphere.bidsphere.dtos.ListingDTO;
import com.bidsphere.bidsphere.dtos.SellerDTO;
import com.bidsphere.bidsphere.entities.*;
import com.bidsphere.bidsphere.repositories.BidsRepository;
import com.bidsphere.bidsphere.repositories.ListingImagesRepository;
import com.bidsphere.bidsphere.repositories.ListingsRepository;
import com.bidsphere.bidsphere.repositories.SellersRepository;
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
@RequestMapping("/api/seller")
public class Seller extends SessionizedController {

    private final SellersRepository sellersRepository;
    private final ListingsRepository listingsRepository;
    private final BidsRepository bidsRepository;
    private final ListingImagesRepository listingImagesRepository;

    public Seller(
            SellersRepository sellersRepository,
            ListingsRepository listingsRepository,
            BidsRepository bidsRepository,
            ListingImagesRepository listingImagesRepository
    ) {
        this.sellersRepository = sellersRepository;
        this.listingsRepository = listingsRepository;
        this.bidsRepository = bidsRepository;
        this.listingImagesRepository = listingImagesRepository;
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateProfile() {
        // location change

        return ResponseEntity.ok(true);
    }

    @GetMapping("/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seller found and returned"),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    public ResponseEntity<SellerDTO> getUser(@PathVariable UUID userId) {
        Optional<Sellers> sellerQuery = this.sellersRepository.findById(userId);

        if (sellerQuery.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new SellerDTO(sellerQuery.get()));
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
        for (Listings listing : listingEntries) {
            listingDTOs.add(new ListingDTO(
                    listing,
                    this.getListingImageIds(listing.getId()),
                    this.getLatestBid(listing.getId())
            ));
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
}
