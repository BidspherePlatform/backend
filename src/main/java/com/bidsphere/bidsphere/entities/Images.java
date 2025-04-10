package com.bidsphere.bidsphere.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class Images {
    @Id
    private UUID id;

    @Column(nullable = false)
    private Date uploadTime;
}
