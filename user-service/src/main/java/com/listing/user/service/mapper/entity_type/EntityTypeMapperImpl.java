package com.listing.user.service.mapper.entity_type;

import com.listing.user.service.entity.EntityTypesEntity;
import com.listing.user.service.model.dto.EntityTypesEntityDto;

public class EntityTypeMapperImpl implements EntityTypeMapper {

    @Override
    public EntityTypesEntityDto entityTypeToDto(EntityTypesEntity entityTypesEntity) {
        return new EntityTypesEntityDto(
                entityTypesEntity.getId(),
                entityTypesEntity.getName(),
                entityTypesEntity.getDescription(),
                entityTypesEntity.getDateCreated()
        );
    }
}
