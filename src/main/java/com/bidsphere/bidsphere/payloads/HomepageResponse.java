package com.bidsphere.bidsphere.payloads;

import com.bidsphere.bidsphere.dtos.ListingDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HomepageResponse {
    private List<ListingDTO> newListings;
    private List<ListingDTO> trendingListings;
    private List<ListingDTO> popularListings;
    
    public HomepageResponse(
            List<ListingDTO> newListings,
            List<ListingDTO> trendingListings,
            List<ListingDTO> popularListings
    ) {
        this.newListings = newListings;
        this.trendingListings = trendingListings;
        this.popularListings = popularListings;
    }
}
