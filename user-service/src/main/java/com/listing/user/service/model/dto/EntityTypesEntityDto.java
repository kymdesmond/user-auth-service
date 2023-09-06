package com.listing.user.service.model.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public record EntityTypesEntityDto(
        int id,
        String name,
        String description,
        Timestamp dateCreated) implements Serializable {
}
