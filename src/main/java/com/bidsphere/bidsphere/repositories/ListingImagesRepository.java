package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.ListingImages;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ListingImagesRepository extends CrudRepository<ListingImages, UUID> {
    List<ListingImages> findAllByListingId(UUID listingId);
}

