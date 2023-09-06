package com.listing.user.service.mapper.entity;

import com.listing.user.service.entity.EntitiesEntity;
import com.listing.user.service.model.dto.EntitiesEntityDto;

public class EntityMapperImpl implements EntityMapper{
    @Override
    public EntitiesEntityDto convertValue(EntitiesEntity entitiesEntity) {
        return new EntitiesEntityDto(
                entitiesEntity.getId(),
                entitiesEntity.getEntityType().getId(),
                entitiesEntity.getEntityType().getName(),
                entitiesEntity.getName(),
                entitiesEntity.getMsisdn(),
                entitiesEntity.getEmail(),
                entitiesEntity.getWebsite(),
                entitiesEntity.getAddress(),
                entitiesEntity.getDateCreated(),
                entitiesEntity.getCreatedBy(),
                entitiesEntity.getStatus()
        );
    }
}
