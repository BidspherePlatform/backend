package com.bidsphere.bidsphere.payloads;

import com.bidsphere.bidsphere.dtos.ListingDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CatalogResponse {
    private List<ListingDTO> listings;

    public CatalogResponse(List<ListingDTO> listings) {
        this.listings = listings;
    }
}
