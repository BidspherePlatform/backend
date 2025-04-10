package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.components.RESTResponse;
import com.bidsphere.bidsphere.dtos.CustomerDTO;
import com.bidsphere.bidsphere.dtos.ProfileDTO;
import com.bidsphere.bidsphere.dtos.SellerDTO;
import com.bidsphere.bidsphere.dtos.UserDTO;
import com.bidsphere.bidsphere.entities.*;
import com.bidsphere.bidsphere.payloads.CredentialsLoginRequest;
import com.bidsphere.bidsphere.payloads.LoginResponse;
import com.bidsphere.bidsphere.payloads.TokenLoginRequest;
import com.bidsphere.bidsphere.repositories.*;
import com.bidsphere.bidsphere.services.PasswordHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class Authentication extends SessionizedController {

    private PasswordHandler passwordHandler;

    private final CredentialsRepository credentialsRepository;
    private final UsersRepository usersRepository;
    private final CustomersRepository customersRepository;
    private final SellersRepository sellersRepository;

    public Authentication(
            SessionsRepository sessionsRepository,
            CredentialsRepository credentialsRepository,
            UsersRepository usersRepository,
            CustomersRepository customersRepository,
            SellersRepository sellersRepository
    ) {
        super(sessionsRepository);
        this.credentialsRepository = credentialsRepository;
        this.usersRepository = usersRepository;
        this.customersRepository = customersRepository;
        this.sellersRepository = sellersRepository;
    }


    @Autowired
    public void setPasswordHandler(PasswordHandler passwordHandler) {
        this.passwordHandler = passwordHandler;
    }

    ProfileDTO getProfile(UUID userId) {
        Optional<Users> userQuery = this.usersRepository.findById(userId);
        Optional<Customers> customerQuery = this.customersRepository.findById(userId);
        Optional<Sellers> sellerQuery = this.sellersRepository.findById(userId);

        if (userQuery.isEmpty() || customerQuery.isEmpty()) {
            return null;
        }

        return new ProfileDTO(
                new UserDTO(userQuery.get()),
                new CustomerDTO(customerQuery.get()),
                new SellerDTO(sellerQuery.orElse(null))
        );
    }

    @PostMapping("/api/authenticate/token")
    public RESTResponse<LoginResponse> authenticateToken(@RequestBody TokenLoginRequest tokenLogin) {
        Optional<Sessions> sessionQuery = this.sessionsRepository.findByToken(tokenLogin.getToken());
        if (sessionQuery.isEmpty()) {
            return RESTResponse.failed("Invalid session!");
        }

        ProfileDTO profile = this.getProfile(sessionQuery.get().getUserId());
        if (profile == null) {
            return RESTResponse.failed("Invalid profile!");
        }
        LoginResponse loginResponse = new LoginResponse(profile, tokenLogin.getToken());

        return RESTResponse.passed(loginResponse);
    }

    @PostMapping("/api/authenticate/credentials")
    public RESTResponse<LoginResponse> authenticateCredentials(@RequestBody CredentialsLoginRequest credentialsLogin) {
        Optional<Credentials> credentials = this.credentialsRepository.findOneByUsername(credentialsLogin.getUsername());

        if (credentials.isEmpty()
                || !this.passwordHandler.hashMatches(
                credentialsLogin.getPassword(),
                credentials.get().getPasswordHash()
        )
        ) {
            return RESTResponse.failed("Invalid credentials!");
        }

        UUID userId = credentials.get().getUserId();

        // Check platform access
//        if (workerProfile.isPresent() && (
//                workerProfile.get().isBanned()
//                        || (workerProfile.get().getSuspension() != null
//                        && workerProfile.get().getSuspension().toInstant().isAfter(dateRightNow))
//        )) {
//            return RESTResponse.failed("Worker cannot access platform!");
//        }

        Optional<Sessions> existingSession = this.sessionsRepository.findByUserId(userId);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 90);

        Sessions session = new Sessions(
                userId,
                existingSession.isEmpty() ? UUID.randomUUID().toString() : existingSession.get().getToken(),
                calendar.getTime()
        );

        if (existingSession.isEmpty()) {
            this.sessionsRepository.save(session);
        }

        ProfileDTO profile = this.getProfile(userId);
        if (profile == null) {
            return RESTResponse.failed("Invalid profile!");
        }
        LoginResponse loginResponse = new LoginResponse(profile, session.getToken());

        return RESTResponse.passed(loginResponse);
    }

    @PostMapping("/api/logout")
    public RESTResponse<Boolean> logout() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty()) {
            return RESTResponse.failed("Invalid session!");
        }

        this.sessionsRepository.deleteByToken(sessions.get().getToken());
        return RESTResponse.passed(true);
    }

}
