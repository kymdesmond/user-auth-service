package com.listing.user.auth.model.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public record EntityTypesEntityDto(int id, String name, String description,
                                   Timestamp dateCreated) implements Serializable {
}
