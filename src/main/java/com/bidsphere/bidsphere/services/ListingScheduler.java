package com.bidsphere.bidsphere.services;

import com.bidsphere.bidsphere.entities.Listings;
import com.bidsphere.bidsphere.repositories.ListingsRepository;
import com.bidsphere.bidsphere.types.ListingStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ListingScheduler {

    private final ListingsRepository listingsRepository;

    public ListingScheduler(ListingsRepository listingsRepository) {
        this.listingsRepository = listingsRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void fetchExpiredListings() {
        Date now = new Date();
        List<Listings> completedListings = listingsRepository
                .findByEndDateBeforeAndStatusLessThanEqual(now, ListingStatus.ACTIVE);

        for (Listings listing : completedListings) {
            listing.setStatus(ListingStatus.COMPLETED);
        }

        listingsRepository.saveAll(completedListings);
    }
}
