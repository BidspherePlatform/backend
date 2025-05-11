package com.bidsphere.bidsphere.services;

import com.bidsphere.bidsphere.entities.Bids;
import com.bidsphere.bidsphere.entities.Listings;
import com.bidsphere.bidsphere.entities.Transactions;
import com.bidsphere.bidsphere.repositories.BidsRepository;
import com.bidsphere.bidsphere.repositories.ListingsRepository;
import com.bidsphere.bidsphere.repositories.TransactionsRepository;
import com.bidsphere.bidsphere.types.ListingStatus;
import org.hibernate.TransactionException;
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
    private final EthereumService ethereumService;

    public ListingScheduler(
            ListingsRepository listingsRepository,
            BidsRepository bidsRepository,
            TransactionsRepository transactionsRepository,
            EthereumService ethereumService
    ) {
        this.listingsRepository = listingsRepository;
        this.bidsRepository = bidsRepository;
        this.transactionsRepository = transactionsRepository;
        this.ethereumService = ethereumService;
    }

    @Scheduled(fixedRate = 60000)
    public void fetchExpiredListings() throws Exception {
        Date now = new Date();
        List<Listings> completedListings = listingsRepository.findByEndDateBeforeAndStatusEquals(now, ListingStatus.ACTIVE);

        for (Listings listing : completedListings) {

            if (transactionsRepository.existsByListingId(listing.getListingId())) {
                continue;
            }

            Optional<Bids> bidQuery = this.bidsRepository.findLatestBidByListingId(listing.getListingId());

            if (bidQuery.isEmpty()) {
                listing.setStatus(ListingStatus.ERRORED);
                continue;
            }

            try {
                this.ethereumService.contract.endListing(EthereumService.uuidToBytes(listing.getListingId())).send();
            } catch (Exception e) {
                System.err.println("Revert reason: " + e.getMessage());
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
