package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.Bids;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BidsRepository extends CrudRepository<Bids, UUID> {
    @Query("SELECT b FROM Bids b WHERE b.listingId = :listingId ORDER BY b.bidDate DESC LIMIT 1")
    Optional<Bids> findLatestBidByListingId(@Param("listingId") UUID listingId);
}
