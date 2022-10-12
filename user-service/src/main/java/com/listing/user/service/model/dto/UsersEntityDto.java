package com.listing.user.service.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class UsersEntityDto implements Serializable {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final int phone;
    private final String email;
    private final Timestamp dateCreated;
    private final Timestamp dateUpdated;
    private final Integer createdBy;
}
