package com.bidsphere.bidsphere.payloads;

import com.bidsphere.bidsphere.dtos.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    UserDTO profile;
    String token;

    public LoginResponse(UserDTO profile, String token) {
        this.profile = profile;
        this.token = token;
    }

    public LoginResponse() {}
}
