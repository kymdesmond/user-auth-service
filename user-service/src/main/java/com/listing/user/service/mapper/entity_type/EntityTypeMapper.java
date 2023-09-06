package com.listing.user.service.mapper.entity_type;

import com.listing.user.service.entity.EntityTypesEntity;
import com.listing.user.service.model.dto.EntityTypesEntityDto;
import org.mapstruct.factory.Mappers;

public interface EntityTypeMapper {

    EntityTypeMapper INSTANCE = Mappers.getMapper(EntityTypeMapper.class);

    EntityTypesEntityDto entityTypeToDto(EntityTypesEntity entityTypesEntity);
}
