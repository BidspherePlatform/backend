package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.ListingDTO;
import com.bidsphere.bidsphere.dtos.ProductCreationDTO;
import com.bidsphere.bidsphere.dtos.ProductDTO;
import com.bidsphere.bidsphere.dtos.ProductPublishDTO;
import com.bidsphere.bidsphere.entities.*;
import com.bidsphere.bidsphere.repositories.*;
import com.bidsphere.bidsphere.services.EthereumService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/product")
public class Product extends SessionizedController {

    private final ListingsRepository listingsRepository;
    private final ListingImagesRepository listingImagesRepository;
    private final ProductsRepository productsRepository;
    private final EthereumService ethereumService;
    private final UsersRepository usersRepository;

    public Product(
            ListingsRepository listingsRepository,
            ListingImagesRepository listingImagesRepository,
            ProductsRepository productsRepository,
            EthereumService ethereumService,
            UsersRepository usersRepository
    ) {
        this.listingsRepository = listingsRepository;
        this.listingImagesRepository = listingImagesRepository;
        this.productsRepository = productsRepository;
        this.ethereumService = ethereumService;
        this.usersRepository = usersRepository;
    }

    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Listing created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid product authenticity - cannot create the listing")
    })
    public ResponseEntity<ProductDTO> create(@RequestBody ProductCreationDTO productCreation) throws Exception {
        Sessions session = this.getSession();
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UUID productId = UUID.randomUUID();
        UUID ownerId = session.getUserId();

        Optional<Users> userEntry = this.usersRepository.findById(ownerId);
        if (userEntry.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Users user = userEntry.get();

        if (Objects.equals(user.getWalletAddress(), "")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        ProductDTO productDTO = new ProductDTO(productId, ownerId, productCreation);
        Products products = new Products(productDTO);

        this.ethereumService.contract.registerProduct(EthereumService.uuidToBytes(productId), user.getWalletAddress()).send();
        this.productsRepository.save(products);

        return ResponseEntity.created(URI.create("/api/product/" + productId)).body(productDTO);
    }

    @PostMapping("/publish")
    @ApiResponses(value = {})
    public ResponseEntity<ListingDTO> publishListing(@RequestBody ProductPublishDTO productPublish) throws Exception {
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
        if (!products.getOwnerId().equals(sellerId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Users> userEntry = this.usersRepository.findById(sellerId);
        if (userEntry.isEmpty()) {
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

        Users user = userEntry.get();
        this.ethereumService.contract.createListing(
                EthereumService.uuidToBytes(listingId),
                EthereumService.uuidToBytes(products.getProductId()),
                user.getWalletAddress(),
                BigDecimal.valueOf(listing.getStartingPrice()).toBigInteger()
        ).send();

        this.listingImagesRepository.saveAll(images);
        this.listingsRepository.save(listing);

        return ResponseEntity.created(URI.create("/api/listing/" + listingId)).body(listingDTO);
    }
}
