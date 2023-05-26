package com.listing.user.service.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UsersEntityDto(long id, String firstName, String lastName, String username, String phone, String email,
                             Timestamp dateCreated, Timestamp dateUpdated) {
}
