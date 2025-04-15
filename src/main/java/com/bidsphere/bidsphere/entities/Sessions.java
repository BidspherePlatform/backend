package com.bidsphere.bidsphere.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Sessions {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Date expiry;

    protected Sessions() {}

    public static Sessions forUser(UUID userId) {
        Sessions sessions = new Sessions();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 14);

        sessions.setId(UUID.randomUUID());
        sessions.setUserId(userId);
        sessions.setToken(UUID.randomUUID().toString());
        sessions.setExpiry(calendar.getTime());

        return sessions;
    }
}