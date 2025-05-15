package com.bidsphere.bidsphere.entities;

import com.bidsphere.bidsphere.dtos.BidDTO;
import com.bidsphere.bidsphere.payloads.BidRequest;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@Entity
public class Bids {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID listingId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private double bidPrice;

    @Column(nullable = false)
    private Date bidDate;

    @Column(nullable = false)
    private String transactionHash;

    protected Bids() {}

    public Bids(BidRequest bidRequest) {
        this.id = UUID.randomUUID();
        this.listingId = bidRequest.getListingId();
        this.userId = bidRequest.getUserId();
        this.bidPrice = bidRequest.getAmount();
        this.transactionHash = bidRequest.getTransactionHash();
        this.bidDate = new Date();
    }

    public Bids(BidDTO bidDTO) {
        this.id = UUID.randomUUID();
        this.listingId = bidDTO.getListingId();
        this.userId = bidDTO.getUserId();
        this.bidPrice = bidDTO.getBidPrice();
        this.bidDate = bidDTO.getBidDate();
        this.transactionHash = bidDTO.getTransactionHash();
    }
}
