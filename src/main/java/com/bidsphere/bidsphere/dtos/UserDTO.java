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
    private static final int STARTER_REPUTATION = 1;

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

    public static UserDTO fromRegistrationDTO(UUID userId, UserRegistrationDTO userRegistrationDTO) {
        UserDTO userDTO = new UserDTO();
        userDTO.id = userId;
        userDTO.setName(userRegistrationDTO.getName());
        userDTO.setAvatarId(userRegistrationDTO.getAvatarId());
        userDTO.setWalletAddress(userRegistrationDTO.getWalletAddress());
        userDTO.setDeliveryLocation(userRegistrationDTO.getDeliveryLocation());
        userDTO.setRegistrationDate(new Date());
        userDTO.setPlatformAccess(PlatformAccess.UNRESTRICTED);
        userDTO.setReputation(STARTER_REPUTATION);

        return userDTO;
    }
}
