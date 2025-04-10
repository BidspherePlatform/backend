package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.components.RESTResponse;
import com.bidsphere.bidsphere.dtos.ProfileDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
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

    @PostMapping("/api/register")
    public RESTResponse<String> register(@RequestBody RegistrationRequest registrationRequest) {
        UUID userId = UUID.randomUUID();
        String username = registrationRequest.getUsername();
        String passwordHash = this.passwordHandler.toHash(registrationRequest.getPassword());
        String email = registrationRequest.getEmail();

        if (this.credentialsRepository.existsByUsername(username)) {
            return RESTResponse.failed("User already exists!");
        }

        Credentials credentialsEntry = new Credentials(userId, username, passwordHash, email);
        ProfileDTO profile = registrationRequest.getProfile();
        Users user = new Users(userId, profile.getUser());
        Customers customer = new Customers(userId, profile.getCustomer());

        this.usersRepository.save(user);
        this.customersRepository.save(customer);
        this.credentialsRepository.save(credentialsEntry);

        if (profile.getSeller() != null) {
            Sellers seller = new Sellers(userId, profile.getSeller());
            this.sellersRepository.save(seller);
        }

        System.out.println("Worked!!!");

        return RESTResponse.passed(userId.toString());
    }
}
