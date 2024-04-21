package com.listing.user.service.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UsersEntityDto(
        int id,
        String firstName,
        String lastName,
        String username,
        String phone,
        String email,
        String avatar,
        String bio,
        String entity,
        int entityId,
        List<String> roles,
        Timestamp dateCreated,
        Timestamp dateUpdated) implements Serializable {
}
