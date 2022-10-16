package com.listing.user.auth.model.auth;

import lombok.Data;

@Data
public class AuthUserRequest {

    private String email;
    private String password;
}
