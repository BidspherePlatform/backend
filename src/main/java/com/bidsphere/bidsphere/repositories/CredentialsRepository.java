package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.Credentials;
import com.bidsphere.bidsphere.entities.Sessions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CredentialsRepository extends CrudRepository<Credentials, UUID> {
    Optional<Credentials> findOneByUsername(@Param("username") String username);
    boolean existsByUsername(@Param("username") String username);
}
