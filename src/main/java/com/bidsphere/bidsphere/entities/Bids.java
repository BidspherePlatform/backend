package com.bidsphere.bidsphere.entities;

import com.bidsphere.bidsphere.dtos.BidDTO;
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

    protected Bids() {}

    public Bids(UUID listingId, UUID userId, double bidPrice) {
        this.id = UUID.randomUUID();
        this.listingId = listingId;
        this.userId = userId;
        this.bidPrice = bidPrice;
        this.bidDate = new Date();
    }

    public Bids(BidDTO bidDTO) {
        this.id = UUID.randomUUID();
        this.listingId = bidDTO.getListingId();
        this.userId = bidDTO.getUserId();
        this.bidPrice = bidDTO.getBidPrice();
        this.bidDate = bidDTO.getBidDate();
    }
}
