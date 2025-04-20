package com.bidsphere.bidsphere.dtos;

import com.bidsphere.bidsphere.entities.Listings;
import com.bidsphere.bidsphere.types.ListingStatus;
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
    private UUID sellerId;
    private String name;
    private String description;
    private Double startingPrice;
    private UUID mainImageId;
    private List<UUID> displayImageIds;
    private String category;
    private String subCategory;
    private Date startDate;
    private Date endDate;
    private boolean bestseller;
    private ListingStatus status;
    private int authenticity;
    private BidDTO latestBid;

    public ListingDTO() {}

    public ListingDTO(Listings listings, ArrayList<UUID> displayImageIds, BidDTO latestBid) {
        this.id = listings.getId();
        this.sellerId = listings.getSellerId();
        this.name = listings.getName();
        this.description = listings.getDescription();
        this.startingPrice = listings.getStartingPrice();
        this.mainImageId = listings.getMainImageId();
        this.displayImageIds = displayImageIds;
        this.category = listings.getCategory();
        this.subCategory = listings.getSubCategory();
        this.startDate = listings.getStartDate();
        this.endDate = listings.getEndDate();
        this.bestseller = listings.isBestseller();
        this.status = listings.getStatus();
        this.authenticity = listings.getAuthenticity();
        this.latestBid = latestBid;
    }
}
