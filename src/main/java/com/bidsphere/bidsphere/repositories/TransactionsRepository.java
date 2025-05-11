package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.Transactions;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface TransactionsRepository extends CrudRepository<Transactions, UUID> {
    ArrayList<Transactions> findAllByPreviousOwnerId(UUID previousOwnerId);

    Optional<Transactions> findByListingId(UUID listingId);

    boolean existsByListingId(UUID listingId);
}
