package com.bidsphere.bidsphere.entities;

import com.bidsphere.bidsphere.dtos.VerificationDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class VerificationTokens {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public VerificationTokens() {}

    public VerificationTokens(VerificationDTO verificationDTO) {
        this.id = verificationDTO.getId();
        this.userId = verificationDTO.getUserId();
        this.token = verificationDTO.getToken();
        this.expiryDate = verificationDTO.getExpiryDate();
    }
}