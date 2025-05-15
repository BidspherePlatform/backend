package com.bidsphere.bidsphere.payloads;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BidRequest {
    private UUID listingId;
    private UUID userId;
    private long amount;
    private String transactionHash;

    public BidRequest() {}
}
