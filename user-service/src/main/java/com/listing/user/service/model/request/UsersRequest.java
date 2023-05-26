package com.listing.user.service.model.request;

public record UsersRequest(String firstName, String lastName, String username, String phone, String email,
                           String password) {
}
