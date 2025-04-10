package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.components.RESTResponse;
import com.bidsphere.bidsphere.repositories.SessionsRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class Seller extends SessionizedController {

    public Seller(
            SessionsRepository sessionsRepository
    ) {
        super(sessionsRepository);
    }

    @PostMapping("/api/seller/updateProfile")
    public RESTResponse<Boolean> updateProfile() {
        // password change
        // location change

        return RESTResponse.passed(true);
    }
}
