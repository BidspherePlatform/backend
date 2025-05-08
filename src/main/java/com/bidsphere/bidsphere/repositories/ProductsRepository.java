package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.Products;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductsRepository extends CrudRepository<Products, UUID> {
    Optional<Products> findByProductId(UUID productId);
}
