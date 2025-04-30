package com.bidsphere.bidsphere.payloads;

import com.bidsphere.bidsphere.dtos.UserRegistrationDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    UserRegistrationDTO userDetails;
    String username;
    String password;
    String email;

    public RegistrationRequest() {}
}