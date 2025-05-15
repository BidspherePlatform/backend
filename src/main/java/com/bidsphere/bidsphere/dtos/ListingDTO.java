package com.bidsphere.bidsphere.dtos;

import com.bidsphere.bidsphere.entities.Listings;
import com.bidsphere.bidsphere.entities.Products;
import com.bidsphere.bidsphere.types.ListingStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ListingDTO extends ProductPublishDTO {
    private UUID listingId;
    private UUID sellerId;
    private String transactionHash;
    private ListingStatus status;
    private ProductDTO product;
    private BidDTO latestBid;
    private TransactionDTO latestTransaction;

    public ListingDTO() {}

    public ListingDTO(Listings listing, ProductDTO product, ArrayList<UUID> displayImageIds, BidDTO latestBid, TransactionDTO latestTransaction) {
        this.listingId = listing.getListingId();
        this.productId = product.getProductId();
        this.sellerId = listing.getSellerId();
        this.transactionHash = listing.getTransactionHash();
        this.startingPrice = listing.getStartingPrice();
        this.startDate = listing.getStartDate();
        this.endDate = listing.getEndDate();
        this.status = listing.getStatus();
        this.mainImageId = listing.getMainImageId();
        this.displayImageIds = displayImageIds;
        this.product = product;
        this.latestBid = latestBid;
        this.latestTransaction  = latestTransaction;
    }

    public ListingDTO(UUID listingId, UUID sellerId, ProductPublishDTO productPublish, ProductDTO product, String transactionHash) {
        this.listingId = listingId;
        this.sellerId = sellerId;
        this.transactionHash = transactionHash;
        this.productId = productPublish.getProductId();
        this.startingPrice = productPublish.getStartingPrice();
        this.startDate = productPublish.getStartDate();
        this.endDate = productPublish.getEndDate();
        this.mainImageId = productPublish.getMainImageId();
        this.displayImageIds = productPublish.getDisplayImageIds();

        this.status = ListingStatus.ACTIVE;
        this.product = product;
    }
}
