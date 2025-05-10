package com.bidsphere.bidsphere.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserUpdateDTO {
    private UUID avatarId;
    private String deliveryLocation;
    private String email;
    private String currentPassword;
    private String newPassword;

    public UserUpdateDTO() {}
}
