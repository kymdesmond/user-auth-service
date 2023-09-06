package com.listing.user.service.mapper.entity;

import com.listing.user.service.entity.EntitiesEntity;
import com.listing.user.service.model.dto.EntitiesEntityDto;
import org.mapstruct.factory.Mappers;

public interface EntityMapper {

    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    EntitiesEntityDto convertValue(EntitiesEntity entitiesEntity);
}
