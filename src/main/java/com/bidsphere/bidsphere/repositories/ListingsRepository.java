package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.Listings;
import com.bidsphere.bidsphere.types.ListingStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ListingsRepository extends CrudRepository<Listings, UUID> {
    List<Listings> findAll(Pageable pageable);
    ArrayList<Listings> findAllByStatus(ListingStatus status, Pageable pageable);
    ArrayList<Listings> findAllBySellerIdAndStatusGreaterThan(UUID sellerId, ListingStatus status, Pageable pageable);
    ArrayList<Listings> findAllBySellerIdAndStatusLessThanEqual(UUID sellerId, ListingStatus status, Pageable pageable);
    List<Listings> findByEndDateBeforeAndStatusLessThanEqual(Date currentTime, ListingStatus status);

    boolean existsByProductId(UUID productId);
}
