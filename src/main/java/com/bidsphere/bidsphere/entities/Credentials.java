package com.bidsphere.bidsphere.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Credentials {
    @Id
    private UUID userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean emailVerified;

    protected Credentials() {}

    public Credentials(UUID userId, String username, String passwordHash, String email) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
    }
}
