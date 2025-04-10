package com.bidsphere.bidsphere.entities;

import com.bidsphere.bidsphere.dtos.SellerDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Sellers {
    @Id
    private UUID userId;

    @Column(nullable = false)
    private int reputation;

    @Column(nullable = false)
    private String pickupLocation;

    protected Sellers() {}

    public Sellers(UUID userId, SellerDTO sellerDTO) {
        this.userId = userId;
        this.reputation = sellerDTO.getReputation();
        this.pickupLocation = sellerDTO.getPickupLocation();
    }
}
