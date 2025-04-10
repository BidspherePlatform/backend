package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.Sellers;
import com.bidsphere.bidsphere.entities.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SellersRepository extends CrudRepository<Sellers, UUID> {
}
