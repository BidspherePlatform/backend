package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.Sessions;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SessionsRepository extends CrudRepository<Sessions, UUID> {
    Optional<Sessions> findByUserId(@Param("userId") UUID userId);
    Optional<Sessions> findByToken(@Param("token") String token);
    @Transactional
    void deleteByToken(@Param("token") String token);
}
