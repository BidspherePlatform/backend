package com.bidsphere.bidsphere.dtos;

import com.bidsphere.bidsphere.entities.Bids;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class BidDTO {
    private UUID listingId;
    private UUID userId;
    private double bidPrice;
    private Date bidDate;
    private String transactionHash;

    public BidDTO() {}

    public BidDTO(Bids bid) {
        this.listingId = bid.getListingId();
        this.userId = bid.getUserId();
        this.bidPrice = bid.getBidPrice();
        this.bidDate = bid.getBidDate();
        this.transactionHash = bid.getTransactionHash();
    }
}
