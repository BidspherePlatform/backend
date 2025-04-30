package com.bidsphere.bidsphere.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserRegistrationDTO {
    protected String name;
    protected UUID avatarId;
    protected String walletAddress;
    protected String deliveryLocation;

    public UserRegistrationDTO() {}
}
