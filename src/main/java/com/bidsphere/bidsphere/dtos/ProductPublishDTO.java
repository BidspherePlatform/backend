package com.bidsphere.bidsphere.dtos;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Getter
public class ProductPublishDTO {
    protected UUID productId;
    protected Double startingPrice;
    protected Date startDate;
    protected Date endDate;
    protected UUID mainImageId;
    protected ArrayList<UUID> displayImageIds;

    public ProductPublishDTO() {}
}
