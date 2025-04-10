package com.bidsphere.bidsphere.payloads;

import com.bidsphere.bidsphere.types.ProductCategories;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CatalogRequest {
    private String textQuery;
    private List<ProductCategories> categories;
    private long priceStart;
    private long priceEnd;
}
