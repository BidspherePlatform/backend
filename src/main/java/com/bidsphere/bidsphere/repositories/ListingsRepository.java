package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.Bids;
import com.bidsphere.bidsphere.entities.Listings;
import com.bidsphere.bidsphere.types.ListingStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface ListingsRepository extends CrudRepository<Listings, UUID> {
    List<Listings> findAll(Pageable pageable);
    ArrayList<Listings> findAllByStatus(ListingStatus status, Pageable pageable);
    ArrayList<Listings> findAllBySellerIdAndStatusGreaterThan(UUID sellerId, ListingStatus status, Pageable pageable);
    ArrayList<Listings> findAllBySellerIdAndStatusLessThanEqual(UUID sellerId, ListingStatus status, Pageable pageable);
    List<Listings> findByEndDateBeforeAndStatusLessThanEqual(Date currentTime, ListingStatus status);

    @Query("SELECT b FROM Listings b WHERE b.status = 2 ORDER BY b.startDate DESC")
    ArrayList<Listings> findLatestListings(Pageable pageable);

    boolean existsByProductId(UUID productId);
}
