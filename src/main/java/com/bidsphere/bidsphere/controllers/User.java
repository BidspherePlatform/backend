package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.components.RESTResponse;
import com.bidsphere.bidsphere.repositories.SessionsRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class User extends SessionizedController {

    public User(SessionsRepository sessionsRepository) {
        super(sessionsRepository);
    }

    @PostMapping("/api/user/updateProfile")
    public RESTResponse<Boolean> updateProfile() {
        return RESTResponse.passed(true);
    }
}
