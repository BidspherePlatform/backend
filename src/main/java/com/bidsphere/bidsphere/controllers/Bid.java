package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.BidDTO;
import com.bidsphere.bidsphere.entities.Bids;
import com.bidsphere.bidsphere.entities.Listings;
import com.bidsphere.bidsphere.entities.Sessions;
import com.bidsphere.bidsphere.entities.Users;
import com.bidsphere.bidsphere.payloads.BidRequest;
import com.bidsphere.bidsphere.repositories.BidsRepository;
import com.bidsphere.bidsphere.repositories.ListingsRepository;
import com.bidsphere.bidsphere.repositories.UsersRepository;
import com.bidsphere.bidsphere.services.EthereumService;
import com.bidsphere.bidsphere.types.ListingStatus;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/bid")
public class Bid extends SessionizedController {

    private EthereumService ethereumService;

    private final BidsRepository bidsRepository;
    private final ListingsRepository listingsRepository;
    private final UsersRepository usersRepository;

    private final HashMap<UUID, HashMap<UUID, Instant>> bids = new HashMap<>();

    public Bid(
            BidsRepository bidsRepository,
            ListingsRepository listingsRepository,
            UsersRepository usersRepository
    ) {
        this.bidsRepository = bidsRepository;
        this.listingsRepository = listingsRepository;
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setEthereumService(EthereumService ethereumService) {
        this.ethereumService = ethereumService;
    }

    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bid created successfully"),
            @ApiResponse(responseCode = "400", description = "Bid amount is too low"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - session is missing or does not match user"),
            @ApiResponse(responseCode = "404", description = "Listing not found")
    })
    public ResponseEntity<BidDTO> createBid(@RequestBody BidRequest bidRequest) throws Exception {
        Sessions session = this.getSession();
        if (session == null || !session.getUserId().equals(bidRequest.getUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Users> userQuery = this.usersRepository.findById(bidRequest.getUserId());
        if (userQuery.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Listings> listingsQuery = this.listingsRepository.findById(bidRequest.getListingId());
        if (listingsQuery.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Users user = userQuery.get();
        Listings listing = listingsQuery.get();

        if (user.getId().equals(listing.getSellerId()) || listing.getStatus() != ListingStatus.ACTIVE) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!this.bids.containsKey(listing.getId())) {
            this.bids.put(listing.getId(), new HashMap<>());
        }

        if (this.bids.get(listing.getId()).containsKey(user.getId())) {
            Instant bidInstant = this.bids.get(listing.getId()).get(user.getId());
            Duration bidDuration = Duration.between(bidInstant, Instant.now());

            if (Math.abs(bidDuration.toMinutes()) < 1) {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            }

            this.bids.get(listing.getId()).remove(user.getId());
        }

        this.bids.get(listing.getId()).put(user.getId(), Instant.now());

        Optional<Bids> bidsQuery = this.bidsRepository.findLatestBidByListingId(bidRequest.getListingId());
        double highestBid = bidsQuery.map(Bids::getBidPrice).orElseGet(listing::getStartingPrice);

        if (bidsQuery.isPresent() && bidsQuery.get().getUserId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        }

        if (bidRequest.getAmount() <= highestBid) {
            return ResponseEntity.badRequest().build();
        }

        double userAmount = this.ethereumService.getUSDBalance(user.getWalletAddress());

        if (bidRequest.getAmount() >= userAmount) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        this.bids.get(listing.getId()).put(user.getId(), Instant.now());

        Bids currentBid = new Bids(bidRequest.getListingId(), bidRequest.getUserId(), bidRequest.getAmount());
        this.bidsRepository.save(currentBid);

        return ResponseEntity.ok(new BidDTO(currentBid));
    }
}
