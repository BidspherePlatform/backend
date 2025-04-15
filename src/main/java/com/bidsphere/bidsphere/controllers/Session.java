package com.bidsphere.bidsphere.controllers;

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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/session")
public class Session extends SessionizedController {

    private PasswordHandler passwordHandler;

    private final CredentialsRepository credentialsRepository;
    private final UsersRepository usersRepository;
    private final CustomersRepository customersRepository;
    private final SellersRepository sellersRepository;

    public Session(
            CredentialsRepository credentialsRepository,
            UsersRepository usersRepository,
            CustomersRepository customersRepository,
            SellersRepository sellersRepository
    ) {
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

    private String renewToken(UUID userId, String token) {
        this.sessionsRepository.deleteByToken(token);
        Sessions renewedSession = Sessions.forUser(userId);
        this.sessionsRepository.save(renewedSession);

        return renewedSession.getToken();
    }

    @PostMapping("/renew")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session renewed successfully"),
            @ApiResponse(responseCode = "404", description = "Session or user not found")
    })
    public ResponseEntity<LoginResponse> renewSession(@RequestBody TokenLoginRequest tokenLogin) {
        Optional<Sessions> sessionQuery = this.sessionsRepository.findByToken(tokenLogin.getToken());
        if (sessionQuery.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ProfileDTO profile = this.getProfile(sessionQuery.get().getUserId());
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }

        String renewedToken = this.renewToken(sessionQuery.get().getUserId(), tokenLogin.getToken());
        LoginResponse loginResponse = new LoginResponse(profile, renewedToken);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Bad Request - User profile not found")
    })
    public ResponseEntity<LoginResponse> createSession(@RequestBody CredentialsLoginRequest credentialsLogin) {
        Optional<Credentials> credentials = this.credentialsRepository.findOneByUsername(credentialsLogin.getUsername());

        if (credentials.isEmpty()
                || !this.passwordHandler.hashMatches(
                credentialsLogin.getPassword(),
                credentials.get().getPasswordHash()
        )
        ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UUID userId = credentials.get().getUserId();
        ProfileDTO profile = this.getProfile(userId);
        if (profile == null) {
            return ResponseEntity.badRequest().build();
        }

        // Check platform access
//        if (workerProfile.isPresent() && (
//                workerProfile.get().isBanned()
//                        || (workerProfile.get().getSuspension() != null
//                        && workerProfile.get().getSuspension().toInstant().isAfter(dateRightNow))
//        )) {
//            return RESTResponse.failed("Worker cannot access platform!");
//        }

        Sessions session = Sessions.forUser(userId);
        this.sessionsRepository.save(session);

        return ResponseEntity.ok(new LoginResponse(profile, session.getToken()));
    }

    @PostMapping("/delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session deleted successfully"),
            @ApiResponse(responseCode = "404", description = "No valid session found")
    })
    public ResponseEntity<String> deleteSession() {
        Sessions sessions = this.getSession();
        if (sessions == null) {
            return ResponseEntity.notFound().build();
        }

        this.sessionsRepository.deleteByToken(sessions.getToken());
        return ResponseEntity.ok(sessions.getToken());
    }

}
