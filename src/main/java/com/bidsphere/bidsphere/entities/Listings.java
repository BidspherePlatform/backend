package com.bidsphere.bidsphere.entities;

import com.bidsphere.bidsphere.dtos.ListingDTO;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@Entity
public class Listings {
    @Id
    private UUID id;

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
    private Date date;

    @Column(nullable = false)
    private boolean bestseller;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private int authenticity;

    protected Listings() {}

    public Listings(UUID id, ListingDTO listingDTO) {
        this.id = id;
        this.name = listingDTO.getName();
        this.description = listingDTO.getDescription();
        this.startingPrice = listingDTO.getStartingPrice();
        this.mainImageId = listingDTO.getMainImageId();
        this.category = listingDTO.getCategory();
        this.subCategory = listingDTO.getSubCategory();
        this.date = listingDTO.getDate();
        this.bestseller = listingDTO.isBestseller();
        this.status = listingDTO.getStatus();
        this.authenticity = listingDTO.getAuthenticity();
    }
}
