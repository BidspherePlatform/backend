package com.bidsphere.bidsphere.entities;

import com.bidsphere.bidsphere.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@Entity
public class Users {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column()
    private UUID avatarId;

    @Column(nullable = false)
    private Date registrationDate;

    @Column(nullable = false)
    private int platformAccess;

    protected Users() {}

    public Users(UUID id, UserDTO userDTO) {
        this.id = id;
        this.name = userDTO.getName();
        this.avatarId = userDTO.getAvatarId();
        this.registrationDate = userDTO.getRegistrationDate();
        this.platformAccess = userDTO.getPlatformAccess();
    }
}
