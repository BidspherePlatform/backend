package com.bidsphere.bidsphere.entities;

import com.bidsphere.bidsphere.dtos.UserDTO;
import com.bidsphere.bidsphere.types.PlatformAccess;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Users {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column()
    private UUID avatarId;

    @Column(nullable = false)
    private String walletAddress;

    @Column(nullable = false)
    private Date registrationDate;

    @Column(nullable = false)
    private PlatformAccess platformAccess;

    @Column(nullable = false)
    private int reputation;

    @Column(nullable = false)
    private String deliveryLocation;

    protected Users() {}

    public Users(UUID id, UserDTO userDTO) {
        this.id = id;
        this.name = userDTO.getName();
        this.avatarId = userDTO.getAvatarId();
        this.walletAddress = userDTO.getWalletAddress();
        this.registrationDate = userDTO.getRegistrationDate();
        this.platformAccess = userDTO.getPlatformAccess();
        this.reputation = userDTO.getReputation();
        this.deliveryLocation = userDTO.getDeliveryLocation();
    }
}
