package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.VerificationDTO;
import com.bidsphere.bidsphere.entities.Credentials;
import com.bidsphere.bidsphere.entities.VerificationTokens;
import com.bidsphere.bidsphere.repositories.CredentialsRepository;
import com.bidsphere.bidsphere.repositories.VerificationTokensRepository;
import com.bidsphere.bidsphere.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/verification")
public class Verification {

    private final VerificationTokensRepository verificationTokensRepository;
    private final CredentialsRepository credentialsRepository;
    private final EmailService emailService;

    @Autowired
    public Verification(
            VerificationTokensRepository verificationTokensRepository,
            CredentialsRepository credentialsRepository,
            EmailService emailService
    ) {
        this.verificationTokensRepository = verificationTokensRepository;
        this.credentialsRepository = credentialsRepository;
        this.emailService = emailService;
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestBody VerificationDTO verificationDTO) {
        try {
            Optional<VerificationTokens> tokenEntry = this.verificationTokensRepository.findByToken(verificationDTO.getToken());

            if (tokenEntry.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification token not found");
            }

            VerificationTokens token = tokenEntry.get();

            if (!token.getUserId().equals(verificationDTO.getUserId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token for this user");
            }

            if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification token has expired");
            }

            Optional<Credentials> credentials = credentialsRepository.findById(verificationDTO.getUserId());

            if (credentials.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            Credentials user = credentials.get();
            user.setEmailVerified(true);
            credentialsRepository.save(user);

            this.verificationTokensRepository.delete(token);

            return ResponseEntity.ok("Email verified successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Verification failed: " + e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resendVerification(@RequestBody VerificationDTO verificationDTO) {
        try {
            UUID userId = verificationDTO.getUserId();
            String email = verificationDTO.getEmail();

            Optional<Credentials> credentialsEntry = credentialsRepository.findById(userId);

            if (credentialsEntry.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            Credentials credentials = credentialsEntry.get();

            if (!credentials.getEmail().equals(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email does not match user record");
            }

            if (credentials.isEmailVerified()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already verified");
            }

            this.verificationTokensRepository.deleteByUserId(userId);

            VerificationTokens newToken = createVerificationTokens(credentials);
            emailService.sendVerificationEmail(credentials.getEmail(), credentials.getUserId().toString(), newToken.getToken().toString());

            return ResponseEntity.ok("Verification email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to resend verification: " + e.getMessage());
        }
    }

    @PostMapping("/resend-by-email")
    public ResponseEntity<String> resendVerificationByEmail(@RequestBody VerificationDTO verificationDTO) {
        try {
            String email = verificationDTO.getEmail();

            Optional<Credentials> credentialsEntry = credentialsRepository.findByEmail(email);

            if (credentialsEntry.isEmpty()) {
                return ResponseEntity.ok("If an account with this email exists, a verification link has been sent");
            }

            Credentials user = credentialsEntry.get();

            if (user.isEmailVerified()) {
                return ResponseEntity.ok("If an account with this email exists, a verification link has been sent");
            }

            this.verificationTokensRepository.deleteByUserId(user.getUserId());

            VerificationTokens newToken = createVerificationTokens(user);

            emailService.sendVerificationEmail(user.getEmail(), user.getUserId().toString(), newToken.getToken().toString());

            return ResponseEntity.ok("If an account with this email exists, a verification link has been sent");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process request: " + e.getMessage());
        }
    }

    private VerificationTokens createVerificationTokens(Credentials user) {
        VerificationTokens token = new VerificationTokens();
        token.setUserId(user.getUserId());
        token.setToken(UUID.randomUUID());
        token.setExpiryDate(LocalDateTime.now().plusHours(24));

        return this.verificationTokensRepository.save(token);
    }
}