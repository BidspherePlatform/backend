package com.bidsphere.bidsphere.payloads;

import com.bidsphere.bidsphere.dtos.ProfileDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoginResponse {
    ProfileDTO profile;
    String token;

    public LoginResponse(ProfileDTO profile, String token) {
        this.profile = profile;
        this.token = token;
    }

    public LoginResponse() {}
}
