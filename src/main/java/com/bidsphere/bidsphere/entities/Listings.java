package com.bidsphere.bidsphere.entities;

import com.bidsphere.bidsphere.dtos.ListingDTO;
import com.bidsphere.bidsphere.types.ListingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Listings {
    @Id
    private UUID listingId;

    @Column(nullable = false)
    private UUID sellerId;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private String transactionHash;

    @Column(nullable = false)
    private double startingPrice;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private ListingStatus status;

    @Column(nullable = false)
    private UUID mainImageId;

    protected Listings() {}

    public Listings(ListingDTO listingDTO) {
        this.listingId = listingDTO.getListingId();
        this.sellerId = listingDTO.getSellerId();
        this.productId = listingDTO.getProduct().getProductId();
        this.transactionHash = listingDTO.getTransactionHash();
        this.startingPrice = listingDTO.getStartingPrice();
        this.startDate = listingDTO.getStartDate();
        this.endDate = listingDTO.getEndDate();
        this.status = listingDTO.getStatus();
        this.mainImageId = listingDTO.getMainImageId();
    }
}
