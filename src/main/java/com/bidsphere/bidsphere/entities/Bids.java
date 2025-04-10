package com.bidsphere.bidsphere.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class Bids {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID listingId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private double bidPrice;

    @Column(nullable = false)
    private Date bidDate;
}
