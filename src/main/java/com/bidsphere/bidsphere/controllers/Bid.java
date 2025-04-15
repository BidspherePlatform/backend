package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.BidDTO;
import com.bidsphere.bidsphere.entities.Bids;
import com.bidsphere.bidsphere.entities.Listings;
import com.bidsphere.bidsphere.entities.Sessions;
import com.bidsphere.bidsphere.payloads.BidRequest;
import com.bidsphere.bidsphere.repositories.BidsRepository;
import com.bidsphere.bidsphere.repositories.ListingsRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/bid")
public class Bid extends SessionizedController {

    private final BidsRepository bidsRepository;
    private final ListingsRepository listingsRepository;

    public Bid(
            BidsRepository bidsRepository,
            ListingsRepository listingsRepository
    ) {
        this.bidsRepository = bidsRepository;
        this.listingsRepository = listingsRepository;
    }

    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bid created successfully"),
            @ApiResponse(responseCode = "400", description = "Bid amount is too low"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - session is missing or does not match user"),
            @ApiResponse(responseCode = "404", description = "Listing not found")
    })
    public ResponseEntity<BidDTO> createBid(@RequestBody BidRequest bidRequest) {
        Sessions session = this.getSession();
        if (session == null || session.getUserId() != bidRequest.getUserId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Listings> listingsQuery = this.listingsRepository.findById(bidRequest.getListingId());
        if (listingsQuery.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Listings listing = listingsQuery.get();

        Optional<Bids> bidsQuery = this.bidsRepository.findLatestBidByListingId(bidRequest.getListingId());
        double highestBid = bidsQuery.isPresent() ? bidsQuery.get().getBidPrice() : listing.getStartingPrice();

        if (bidRequest.getAmount() <= highestBid) {
            return ResponseEntity.badRequest().build();
        }

        // Perform smart contract

        Bids currentBid = new Bids(bidRequest.getListingId(), bidRequest.getUserId(), bidRequest.getAmount());
        this.bidsRepository.save(currentBid);

        return ResponseEntity.ok(new BidDTO(currentBid));
    }
}
