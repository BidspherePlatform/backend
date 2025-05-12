package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.UserDTO;
import com.bidsphere.bidsphere.entities.Credentials;
import com.bidsphere.bidsphere.entities.Users;
import com.bidsphere.bidsphere.entities.VerificationTokens;
import com.bidsphere.bidsphere.payloads.RegistrationRequest;
import com.bidsphere.bidsphere.repositories.CredentialsRepository;
import com.bidsphere.bidsphere.repositories.UsersRepository;
import com.bidsphere.bidsphere.repositories.VerificationTokensRepository;
import com.bidsphere.bidsphere.services.EmailService;
import com.bidsphere.bidsphere.services.PasswordHandler;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/registration")
public class Registration {

    private final CredentialsRepository credentialsRepository;
    private final UsersRepository usersRepository;
    private final VerificationTokensRepository verificationTokensRepository;

    private PasswordHandler passwordHandler;
    private EmailService emailService;

    public Registration(
            CredentialsRepository credentialsRepository,
            UsersRepository usersRepository,
            VerificationTokensRepository verificationTokensRepository
    ) {
        this.credentialsRepository = credentialsRepository;
        this.usersRepository = usersRepository;
        this.verificationTokensRepository = verificationTokensRepository;
    }

    @Autowired
    public void setPasswordHandler(PasswordHandler passwordHandler) {
        this.passwordHandler = passwordHandler;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "409", description = "Username already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid registration data")
    })
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        UUID userId = UUID.randomUUID();
        String username = registrationRequest.getUsername();
        String passwordHash = this.passwordHandler.toHash(registrationRequest.getPassword());
        String email = registrationRequest.getEmail();

        boolean existsByUsername = this.credentialsRepository.existsByUsername(username);
        boolean existsByEmail = this.credentialsRepository.existsByEmail(email);
        boolean existsByWalletAddress = this.usersRepository.existsByWalletAddress(registrationRequest.getUserDetails().getWalletAddress());

        if (existsByUsername || existsByEmail || existsByWalletAddress) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Credentials credentialsEntry = new Credentials(userId, username, passwordHash, email);
        UserDTO userDTO = UserDTO.fromRegistrationDTO(userId, registrationRequest.getUserDetails());

        try {
            VerificationTokens token = new VerificationTokens();
            token.setUserId(userId);
            token.setToken(UUID.randomUUID());
            token.setExpiryDate(java.time.LocalDateTime.now().plusHours(24));
            this.verificationTokensRepository.save(token);

            this.emailService.sendVerificationEmail(email, userId.toString(), token.getToken().toString());
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
        }

        this.usersRepository.save(new Users(userId, userDTO));
        this.credentialsRepository.save(credentialsEntry);

        return ResponseEntity.ok(userId.toString());
    }

    @PostMapping("/verify/email")
    public ResponseEntity<Boolean> verifyEmail(@RequestBody String email) {
        return ResponseEntity.ok(!this.credentialsRepository.existsByEmail(email));
    }

    @PostMapping("/verify/username")
    public ResponseEntity<Boolean> verifyUsername(@RequestBody String username) {
        return ResponseEntity.ok(!this.credentialsRepository.existsByUsername(username));
    }

    @PostMapping("/verify/walletAddress")
    public ResponseEntity<Boolean> verifyWalletAddress(@RequestBody String walletAddress) {
        return ResponseEntity.ok(!this.usersRepository.existsByWalletAddress(walletAddress));
    }
}
