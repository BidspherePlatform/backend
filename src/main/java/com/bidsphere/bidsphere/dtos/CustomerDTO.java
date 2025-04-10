package com.bidsphere.bidsphere.dtos;

import com.bidsphere.bidsphere.entities.Customers;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CustomerDTO {
    private UUID userID;

    public CustomerDTO() {}

    public CustomerDTO(Customers customer) {
        this.userID = customer.getUserId();
    }
}
