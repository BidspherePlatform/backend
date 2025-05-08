package com.bidsphere.bidsphere.dtos;

import com.bidsphere.bidsphere.entities.Products;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class ProductDTO extends ProductCreationDTO {
    private UUID productId;
    private UUID ownerId;

    public ProductDTO() {}

    public ProductDTO(Products product) {
        this.productId = product.getProductId();
        this.ownerId = product.getOwnerId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.category = product.getCategory();
        this.subCategory = product.getSubCategory();
    }

    public ProductDTO(UUID productId, UUID ownerId, ProductCreationDTO creationDTO) {
        this.productId = productId;
        this.ownerId = ownerId;
        this.name = creationDTO.getName();
        this.description = creationDTO.getDescription();
        this.category = creationDTO.getCategory();
        this.subCategory = creationDTO.getSubCategory();
    }
}
