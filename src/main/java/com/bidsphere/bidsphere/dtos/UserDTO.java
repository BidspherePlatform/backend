package com.bidsphere.bidsphere.dtos;

import com.bidsphere.bidsphere.entities.Users;
import com.bidsphere.bidsphere.types.PlatformAccess;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
public class UserDTO extends UserRegistrationDTO {
    private UUID id;
    private Date registrationDate;
    private PlatformAccess platformAccess;
    private int reputation;

    public UserDTO() {}

    public UserDTO(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.avatarId = user.getAvatarId();
        this.walletAddress = user.getWalletAddress();
        this.registrationDate = user.getRegistrationDate();
        this.platformAccess = user.getPlatformAccess();
        this.reputation = user.getReputation();
        this.deliveryLocation = user.getDeliveryLocation();
    }
}
