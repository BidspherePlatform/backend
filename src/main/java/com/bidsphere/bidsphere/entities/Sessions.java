package com.bidsphere.bidsphere.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@Entity
public class Sessions {
    @Id
    private UUID userId;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Date expiry;

    protected Sessions() {}

    public Sessions(UUID userId, String token, Date expiry) {
        this.userId = userId;
        this.token = token;
        this.expiry = expiry;
    }
}