package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.entities.Sessions;
import com.bidsphere.bidsphere.entities.Users;
import com.bidsphere.bidsphere.repositories.SessionsRepository;
import com.bidsphere.bidsphere.repositories.UsersRepository;
import com.bidsphere.bidsphere.services.Tokenization;
import com.bidsphere.bidsphere.types.PlatformAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class SessionizedController {
    protected SessionsRepository sessionsRepository;
    private UsersRepository usersRepository;

    @Autowired
    public void setSessionsRepository(SessionsRepository sessionsRepository) {
        this.sessionsRepository = sessionsRepository;
    }

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    protected boolean isValidSession() {
        String token = Tokenization.getToken();
        Optional<Sessions> sessionQuery = this.sessionsRepository.findByToken(token);

        return validateSession(sessionQuery);
    }

    protected Sessions getSession() {
        return this.sessionsRepository.findByToken(Tokenization.getToken()).orElse(null);
    }

    protected boolean validateSession(Optional<Sessions> sessionQuery) {
        if (sessionQuery.isEmpty()) {
            return false;
        }

        Sessions session = sessionQuery.get();
        UUID userId = session.getUserId();
        Optional<Users> userQuery = this.usersRepository.findById(userId);
        Instant dateRightNow = new Date().toInstant();
        boolean sessionExpired = sessionQuery.get().getExpiry().toInstant().isBefore(dateRightNow);

        if (sessionExpired) {
            this.sessionsRepository.delete(sessionQuery.get());
        }

        return !sessionExpired
                && userQuery.isPresent()
                && userQuery.get().getPlatformAccess() == PlatformAccess.UNRESTRICTED;
    }
}
