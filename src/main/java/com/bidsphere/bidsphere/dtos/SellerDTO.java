package com.bidsphere.bidsphere.dtos;

import com.bidsphere.bidsphere.entities.Sellers;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SellerDTO {
    private UUID id;
    private int reputation;
    private String pickupLocation;

    public SellerDTO() {}

    public SellerDTO(Sellers seller) {
        if (seller != null) {
            this.id = seller.getUserId();
            this.reputation = seller.getReputation();
            this.pickupLocation = seller.getPickupLocation();
        }
    }
}
