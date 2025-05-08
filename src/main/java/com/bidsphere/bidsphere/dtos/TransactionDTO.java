package com.bidsphere.bidsphere.dtos;

import com.bidsphere.bidsphere.entities.Transactions;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransactionDTO {
    private UUID id;
    private UUID listingId;
    private UUID previousOwnerId;
    private UUID nextOwnerId;
    private UUID bidId;

    public TransactionDTO() {}

    public TransactionDTO(Transactions transactions) {
        this.id = transactions.getId();
        this.listingId = transactions.getListingId();
        this.previousOwnerId = transactions.getPreviousOwnerId();
        this.nextOwnerId = transactions.getNextOwnerId();
        this.bidId = transactions.getBidId();
    }
}
