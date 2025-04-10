package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.Customers;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomersRepository extends CrudRepository<Customers, UUID> {
}
