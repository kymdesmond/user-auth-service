package com.listing.user.auth.model.request;

import lombok.Data;

import java.io.Serializable;

public record UsersRequest(long id, String firstName, String lastName, String username, String phone, String email,
                           String password) implements Serializable {
}
