package com.bidsphere.bidsphere.dtos;

import com.bidsphere.bidsphere.entities.Listings;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ListingDTO {
    private UUID id;
    private String name;
    private String description;
    private Double startingPrice;
    private UUID mainImageId;
    private List<UUID> displayImageIds;
    private String category;
    private String subCategory;
    private Date date;
    private boolean bestseller;
    private int status;
    private int authenticity;

    public ListingDTO() {}

    public ListingDTO(Listings listings, ArrayList<UUID> displayImageIds) {
        this.id = listings.getId();
        this.name = listings.getName();
        this.description = listings.getDescription();
        this.startingPrice = listings.getStartingPrice();
        this.mainImageId = listings.getMainImageId();
        this.displayImageIds = displayImageIds;
        this.category = listings.getCategory();
        this.subCategory = listings.getSubCategory();
        this.date = listings.getDate();
        this.bestseller = listings.isBestseller();
        this.status = listings.getStatus();
        this.authenticity = listings.getAuthenticity();
    }
}
