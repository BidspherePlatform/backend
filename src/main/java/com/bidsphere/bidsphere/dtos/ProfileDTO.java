package com.bidsphere.bidsphere.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    private UserDTO user;
    private CustomerDTO customer;
    private SellerDTO seller;

    public ProfileDTO() {}

    public ProfileDTO(
            UserDTO user,
            CustomerDTO customer,
            SellerDTO seller
    ) {
        this.user = user;
        this.customer = customer;
        this.seller = seller;
    }
}
