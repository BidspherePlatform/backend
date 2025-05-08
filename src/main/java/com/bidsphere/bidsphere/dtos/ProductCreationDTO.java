package com.bidsphere.bidsphere.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class ProductCreationDTO {
    protected String name;
    protected String description;
    protected String category;
    protected String subCategory;

    public ProductCreationDTO() {}
}
