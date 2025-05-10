package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.UserDTO;
import com.bidsphere.bidsphere.entities.Credentials;
import com.bidsphere.bidsphere.entities.Users;
import com.bidsphere.bidsphere.payloads.RegistrationRequest;
import com.bidsphere.bidsphere.repositories.CredentialsRepository;
import com.bidsphere.bidsphere.repositories.UsersRepository;
import com.bidsphere.bidsphere.services.PasswordHandler;
import com.bidsphere.bidsphere.types.PlatformAccess;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/registration")
public class Registration {


    private final CredentialsRepository credentialsRepository;
    private final UsersRepository usersRepository;

    private PasswordHandler passwordHandler;

    public Registration(
            CredentialsRepository credentialsRepository,
            UsersRepository usersRepository
    ) {
        this.credentialsRepository = credentialsRepository;
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setPasswordHandler(PasswordHandler passwordHandler) {
        this.passwordHandler = passwordHandler;
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

        this.usersRepository.save(new Users(userId, userDTO));
        this.credentialsRepository.save(credentialsEntry);

        return ResponseEntity.ok(userId.toString());
    }

    @PostMapping("/verify/email")
    public ResponseEntity<Boolean> verifyEmail(@RequestBody String email) {
        System.out.println(email);
        System.out.println(this.credentialsRepository.existsByEmail(email));
        return ResponseEntity.ok(!this.credentialsRepository.existsByEmail(email));
    }

    @PostMapping("/verify/username")
    public ResponseEntity<Boolean> verifyUsername(@RequestBody String username) {
        System.out.println(username);
        System.out.println(this.credentialsRepository.existsByUsername(username));
        return ResponseEntity.ok(!this.credentialsRepository.existsByUsername(username));
    }

    @PostMapping("/verify/walletAddress")
    public ResponseEntity<Boolean> verifyWalletAddress(@RequestBody String walletAddress) {
        System.out.println(walletAddress);
        System.out.println(this.usersRepository.existsByWalletAddress(walletAddress));
        return ResponseEntity.ok(!this.usersRepository.existsByWalletAddress(walletAddress));
    }
}
