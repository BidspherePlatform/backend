package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.ListingDTO;
import com.bidsphere.bidsphere.dtos.ProductCreationDTO;
import com.bidsphere.bidsphere.dtos.ProductDTO;
import com.bidsphere.bidsphere.dtos.ProductPublishDTO;
import com.bidsphere.bidsphere.entities.ListingImages;
import com.bidsphere.bidsphere.entities.Listings;
import com.bidsphere.bidsphere.entities.Products;
import com.bidsphere.bidsphere.entities.Sessions;
import com.bidsphere.bidsphere.repositories.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/product")
public class Product extends SessionizedController {

    private final ListingsRepository listingsRepository;
    private final ListingImagesRepository listingImagesRepository;
    private final ProductsRepository productsRepository;

    public Product(
            ListingsRepository listingsRepository,
            ListingImagesRepository listingImagesRepository,
            ProductsRepository productsRepository
    ) {
        this.listingsRepository = listingsRepository;
        this.listingImagesRepository = listingImagesRepository;
        this.productsRepository = productsRepository;
    }

    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Listing created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid product authenticity - cannot create the listing")
    })
    public ResponseEntity<ProductDTO> create(@RequestBody ProductCreationDTO productCreation) {
        Sessions session = this.getSession();
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UUID productId = UUID.randomUUID();
        ProductDTO productDTO = new ProductDTO(productId, session.getUserId(), productCreation);
        Products products = new Products(productDTO);
        this.productsRepository.save(products);

        return ResponseEntity.created(URI.create("/api/product/" + productId)).body(productDTO);
    }

    @PostMapping("/publish")
    @ApiResponses(value = {})
    public ResponseEntity<ListingDTO> publishListing(@RequestBody ProductPublishDTO productPublish) {
        Sessions session = this.getSession();
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Products> productsEntry = this.productsRepository.findByProductId(productPublish.getProductId());
        if (productsEntry.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Products products = productsEntry.get();
        if (this.listingsRepository.existsByProductId(products.getProductId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        UUID sellerId = session.getUserId();
        if (products.getOwnerId().equals(sellerId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UUID listingId = UUID.randomUUID();
        ProductDTO productDTO = new ProductDTO(products);
        ListingDTO listingDTO = new ListingDTO(listingId, sellerId, productPublish, productDTO);
        Listings listing = new Listings(listingDTO);

        ArrayList<ListingImages> images = new ArrayList<>();
        for (UUID listingImageId : productPublish.getDisplayImageIds()) {
            images.add(new ListingImages(listingId, listingImageId));
        }

        this.listingImagesRepository.saveAll(images);
        this.listingsRepository.save(listing);

        return ResponseEntity.created(URI.create("/api/listing/" + listingId)).body(listingDTO);
    }
}
