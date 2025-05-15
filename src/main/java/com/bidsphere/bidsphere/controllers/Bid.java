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
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;

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

        Optional<Bids> latestBid = this.bidsRepository.findLatestBidByListingId(listing.getListingId());
        if (latestBid.isPresent() && latestBid.get().getUserId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        }

        if (user.getId().equals(listing.getSellerId()) || listing.getStatus() != ListingStatus.ACTIVE) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!this.bids.containsKey(listing.getListingId())) {
            this.bids.put(listing.getListingId(), new HashMap<>());
        }

        if (this.bids.get(listing.getListingId()).containsKey(user.getId())) {
            Instant bidInstant = this.bids.get(listing.getListingId()).get(user.getId());
            Duration bidDuration = Duration.between(bidInstant, Instant.now());

            if (Math.abs(bidDuration.toMinutes()) < 1) {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            }

            this.bids.get(listing.getListingId()).remove(user.getId());
        }

        this.bids.get(listing.getListingId()).put(user.getId(), Instant.now());

        Optional<Transaction> transactionQuery = this.ethereumService.getTransactionByHash(listing.getTransactionHash());
        if (transactionQuery.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Transaction transaction = transactionQuery.get();
        String senderWallet = transaction.getFrom();
        String senderTarget = transaction.getTo();

        System.out.println("Received transaction from " + senderWallet + " to " + senderTarget + " with transaction [" + transaction.getHash() + "]");

        if (!senderWallet.equals(user.getWalletAddress()) || !this.ethereumService.matchesContractAddress(senderTarget)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        this.bids.get(listing.getListingId()).put(user.getId(), Instant.now());

        Bids currentBid = new Bids(bidRequest);
        this.bidsRepository.save(currentBid);

        return ResponseEntity.ok(new BidDTO(currentBid));
    }

    @GetMapping("/previous/{listingId}")
    public ResponseEntity<BidDTO> previousBid(@PathVariable UUID listingId) {
        Sessions session = this.getSession();
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Bids> bidsEntry = this.bidsRepository.findLatestBidByUserIdOnListing(session.getUserId(), listingId);

        return bidsEntry.map(value -> ResponseEntity.ok(new BidDTO(value))).orElse(null);

    }
}
