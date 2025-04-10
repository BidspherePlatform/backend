package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.components.RESTResponse;
import com.bidsphere.bidsphere.dtos.ListingDTO;
import com.bidsphere.bidsphere.entities.ListingImages;
import com.bidsphere.bidsphere.entities.Listings;
import com.bidsphere.bidsphere.repositories.ListingImagesRepository;
import com.bidsphere.bidsphere.repositories.ListingsRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@CrossOrigin
public class Listing {

    private final ListingsRepository listingsRepository;
    private final ListingImagesRepository listingImagesRepository;

    public Listing(
            ListingsRepository listingsRepository,
            ListingImagesRepository listingImagesRepository
    ) {
        this.listingsRepository = listingsRepository;
        this.listingImagesRepository = listingImagesRepository;
    }

    @PostMapping("/api/listing/create")
    public RESTResponse<String> createListing(@RequestBody ListingDTO listingDTO) {
        UUID listingId = UUID.randomUUID();

        if (listingDTO.getAuthenticity() < 3) {
            return RESTResponse.failed("Authenticity rating is too low!");
        }

        Listings listings = new Listings(listingId, listingDTO);
        this.listingsRepository.save(listings);

        ArrayList<ListingImages> images = new ArrayList<>();
        for (UUID listingImageId : listingDTO.getDisplayImageIds()) {
            images.add(new ListingImages(listingId, listingImageId));
        }

        this.listingImagesRepository.saveAll(images);

        return RESTResponse.passed(listingId.toString());
    }
}
