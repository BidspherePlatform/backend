package com.bidsphere.bidsphere.dtos;

import com.bidsphere.bidsphere.entities.Users;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
public class UserDTO {
    private UUID id;
    private String name;
    private UUID avatarId;
    private Date registrationDate;
    private int platformAccess;

    public UserDTO() {}

    public UserDTO(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.avatarId = user.getAvatarId();
        this.registrationDate = user.getRegistrationDate();
        this.platformAccess = user.getPlatformAccess();
    }
}
