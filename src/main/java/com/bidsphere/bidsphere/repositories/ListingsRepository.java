package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.Listings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ListingsRepository extends CrudRepository<Listings, UUID> {
    List<Listings> findAll(Pageable pageable);
}
