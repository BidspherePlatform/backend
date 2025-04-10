package com.bidsphere.bidsphere.payloads;

import com.bidsphere.bidsphere.dtos.ProfileDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    ProfileDTO profile;
    String username;
    String password;
    String email;

    public RegistrationRequest() {}
}