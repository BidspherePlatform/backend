package com.bidsphere.bidsphere.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsLoginRequest {
    String username;
    String password;

    public CredentialsLoginRequest() {}
}
