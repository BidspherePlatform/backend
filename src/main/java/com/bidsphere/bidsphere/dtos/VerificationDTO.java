package com.bidsphere.bidsphere.dtos;

import com.bidsphere.bidsphere.entities.VerificationTokens;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class VerificationDTO {
    private UUID id;
    private UUID userId;
    private UUID token;
    private String email;
    private LocalDateTime expiryDate;

    public VerificationDTO() {}

    public VerificationDTO(VerificationTokens verificationTokens) {
        this.id = verificationTokens.getId();
        this.userId = verificationTokens.getUserId();
        this.token = verificationTokens.getToken();
        this.expiryDate = verificationTokens.getExpiryDate();
    }
}
