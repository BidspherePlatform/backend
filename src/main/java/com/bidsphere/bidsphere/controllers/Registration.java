package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.ProfileDTO;
import com.bidsphere.bidsphere.dtos.SellerDTO;
import com.bidsphere.bidsphere.dtos.UserDTO;
import com.bidsphere.bidsphere.entities.Credentials;
import com.bidsphere.bidsphere.entities.Customers;
import com.bidsphere.bidsphere.entities.Sellers;
import com.bidsphere.bidsphere.entities.Users;
import com.bidsphere.bidsphere.payloads.RegistrationRequest;
import com.bidsphere.bidsphere.repositories.CredentialsRepository;
import com.bidsphere.bidsphere.repositories.CustomersRepository;
import com.bidsphere.bidsphere.repositories.SellersRepository;
import com.bidsphere.bidsphere.repositories.UsersRepository;
import com.bidsphere.bidsphere.services.PasswordHandler;
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
    private final CustomersRepository customersRepository;
    private final SellersRepository sellersRepository;

    private PasswordHandler passwordHandler;

    public Registration(
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

        if (this.credentialsRepository.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Credentials credentialsEntry = new Credentials(userId, username, passwordHash, email);
        ProfileDTO profile = registrationRequest.getProfile();

        UserDTO userDTO = profile.getUser();
        userDTO.setRegistrationDate(new Date());
        userDTO.setPlatformAccess(0);

        Users user = new Users(userId, userDTO);
        Customers customer = new Customers(userId, profile.getCustomer());

        this.usersRepository.save(user);
        this.customersRepository.save(customer);
        this.credentialsRepository.save(credentialsEntry);

        if (profile.getSeller() != null) {
            SellerDTO sellerDTO = profile.getSeller();
            sellerDTO.setReputation(5);

            Sellers seller = new Sellers(userId, profile.getSeller());
            this.sellersRepository.save(seller);
        }

        return ResponseEntity.ok(userId.toString());
    }
}
