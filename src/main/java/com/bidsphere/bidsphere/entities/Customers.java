package com.bidsphere.bidsphere.entities;

import com.bidsphere.bidsphere.dtos.CustomerDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
public class Customers {
    @Id
    private UUID userId;

    protected Customers() {}

    public Customers(UUID userId, CustomerDTO customerDTO) {
        this.userId = userId;
    }
}
