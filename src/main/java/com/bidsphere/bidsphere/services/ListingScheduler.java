package com.bidsphere.bidsphere.services;

import com.bidsphere.bidsphere.entities.Bids;
import com.bidsphere.bidsphere.entities.Listings;
import com.bidsphere.bidsphere.entities.Transactions;
import com.bidsphere.bidsphere.repositories.BidsRepository;
import com.bidsphere.bidsphere.repositories.ListingsRepository;
import com.bidsphere.bidsphere.repositories.TransactionsRepository;
import com.bidsphere.bidsphere.types.ListingStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ListingScheduler {

    private final ListingsRepository listingsRepository;
    private final BidsRepository bidsRepository;
    private final TransactionsRepository transactionsRepository;

    public ListingScheduler(
            ListingsRepository listingsRepository,
            BidsRepository bidsRepository,
            TransactionsRepository transactionsRepository
    ) {
        this.listingsRepository = listingsRepository;
        this.bidsRepository = bidsRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void fetchExpiredListings() {
        Date now = new Date();
        List<Listings> completedListings = listingsRepository
                .findByEndDateBeforeAndStatusLessThanEqual(now, ListingStatus.ACTIVE);

        for (Listings listing : completedListings) {
            Optional<Bids> bidQuery = this.bidsRepository.findLatestBidByListingId(listing.getId());

            if (bidQuery.isEmpty()) {
                listing.setStatus(ListingStatus.ERRORED);
                continue;
            }

            Bids bid = bidQuery.get();
            Transactions transaction = new Transactions(listing, bid);

            this.transactionsRepository.save(transaction);

            listing.setSellerId(bid.getUserId());
            listing.setStatus(ListingStatus.UNLISTED);
        }

        listingsRepository.saveAll(completedListings);
    }
}
