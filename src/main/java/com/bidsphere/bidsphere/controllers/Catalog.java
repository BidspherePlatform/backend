package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.components.RESTResponse;
import com.bidsphere.bidsphere.entities.ListingImages;
import com.bidsphere.bidsphere.entities.Listings;
import com.bidsphere.bidsphere.payloads.CatalogResponse;
import com.bidsphere.bidsphere.payloads.CatalogRequest;
import com.bidsphere.bidsphere.payloads.HomepageResponse;
import com.bidsphere.bidsphere.dtos.ListingDTO;
import com.bidsphere.bidsphere.repositories.ListingImagesRepository;
import com.bidsphere.bidsphere.repositories.ListingsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class Catalog {

    // Solidify Listings data structure
    // Persistence Entity to Payload mapping
    // Lookup logic
    private final ListingsRepository listingsRepository;
    private final ListingImagesRepository listingImagesRepository;

    public Catalog(
            ListingsRepository listingsRepository,
            ListingImagesRepository listingImagesRepository
    ) {
        this.listingsRepository = listingsRepository;
        this.listingImagesRepository = listingImagesRepository;
    }

    @PostMapping("/api/catalog")
    public RESTResponse<CatalogResponse> getCatalog(@RequestBody CatalogRequest request) {
        List<ListingDTO> listingDTOs = this.getListings();

        return RESTResponse.passed(new CatalogResponse(listingDTOs));
    }

    @GetMapping("/api/homepage")
    public RESTResponse<HomepageResponse> getHomepageListings() {
        HomepageResponse homepageResponse = new HomepageResponse(
                this.getNewListings(),
                this.getTrendingListings(),
                this.getPopularListings()
        );

        return RESTResponse.passed(homepageResponse);
    }

    private List<ListingDTO> getNewListings() {
        return this.getListings();
    }

    private List<ListingDTO> getTrendingListings() {
        return this.getListings();
    }

    private List<ListingDTO> getPopularListings() {
        return this.getListings();
    }

    private List<ListingDTO> getListings() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Listings> listingEntries = this.listingsRepository.findAll(pageable).stream().toList();

        List<ListingDTO> listingDTOs = new ArrayList<>();

        for (Listings listing : listingEntries) {
            List<ListingImages> listingImages = this.listingImagesRepository.findAllByListingId(listing.getId());
            ArrayList<UUID> listingImageIds = new ArrayList<>();

            for (ListingImages listingImage : listingImages) {
                listingImageIds.add(listingImage.getImageId());
            }
            listingDTOs.add(new ListingDTO(listing, listingImageIds));
        }

        return listingDTOs;
    }
}
