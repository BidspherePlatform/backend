package com.bidsphere.bidsphere.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class ListingImages {
    @Id
    private UUID imageId;

    @Column(nullable = false)
    private UUID listingId;

    protected ListingImages() {}

    public ListingImages(UUID listingId, UUID imageId) {
        this.listingId = listingId;
        this.imageId = imageId;
    }
}
