package com.bidsphere.bidsphere.entities;

import com.bidsphere.bidsphere.dtos.ProductDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Products {
    @Id
    private UUID productId;

    @Column(nullable = false)
    private UUID ownerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String subCategory;

    @Column(nullable = false)
    private String transactionHash;

    protected Products() {}

    public Products(ProductDTO productDTO) {
        this.productId = productDTO.getProductId();
        this.ownerId = productDTO.getOwnerId();
        this.name = productDTO.getName();
        this.description = productDTO.getDescription();
        this.category = productDTO.getCategory();
        this.subCategory = productDTO.getSubCategory();
        this.transactionHash = productDTO.getTransactionHash();
    }
}
