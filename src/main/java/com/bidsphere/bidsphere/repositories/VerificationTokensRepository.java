package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.VerificationTokens;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface VerificationTokensRepository extends CrudRepository<VerificationTokens, UUID> {
    Optional<VerificationTokens> findByToken(UUID token);

    @Transactional
    void deleteByUserId(UUID userId);
}
