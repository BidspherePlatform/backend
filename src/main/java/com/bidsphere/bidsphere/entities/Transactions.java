package com.bidsphere.bidsphere.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@Entity
public class Transactions {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID listingId;

    @Column(nullable = false)
    private UUID previousOwnerId;

    @Column(nullable = false)
    private UUID nextOwnerId;

    @Column(nullable = false)
    private UUID bidId;

    @Column(nullable = false)
    private String transactionHash;

    @Column(nullable = false)
    private Date transactionDate;

    protected Transactions() {}

    public Transactions(Listings listing, Bids bid, String transactionHash) {
        this.id = UUID.randomUUID();
        this.transactionHash = transactionHash;
        this.listingId = listing.getListingId();
        this.previousOwnerId = listing.getSellerId();
        this.nextOwnerId = bid.getUserId();
        this.bidId = bid.getId();
        this.transactionDate = new Date();
    }

}
