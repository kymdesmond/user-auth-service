package com.listing.user.service.model.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public record EntitiesEntityDto(int id, Integer entityTypeId, String name, String msisdn, String email, String website,
                                String address, Timestamp dateCreated, Integer createdBy, Integer status)
        implements Serializable {}
