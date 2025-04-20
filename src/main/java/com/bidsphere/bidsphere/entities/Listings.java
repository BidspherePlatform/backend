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
    private UUID id;

    @Column(nullable = false)
    private UUID sellerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double startingPrice;

    @Column(nullable = false)
    private UUID mainImageId;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String subCategory;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private boolean bestseller;

    @Column(nullable = false)
    private ListingStatus status;

    @Column(nullable = false)
    private int authenticity;

    protected Listings() {}

    public Listings(UUID id, ListingDTO listingDTO) {
        this.id = id;
        this.sellerId = listingDTO.getSellerId();
        this.name = listingDTO.getName();
        this.description = listingDTO.getDescription();
        this.startingPrice = listingDTO.getStartingPrice();
        this.mainImageId = listingDTO.getMainImageId();
        this.category = listingDTO.getCategory();
        this.subCategory = listingDTO.getSubCategory();
        this.startDate = listingDTO.getStartDate();
        this.endDate = listingDTO.getEndDate();
        this.bestseller = listingDTO.isBestseller();
        this.status = listingDTO.getStatus();
        this.authenticity = listingDTO.getAuthenticity();
    }
}
