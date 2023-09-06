package com.listing.user.service.mapper.user;

import com.listing.user.service.entity.UsersEntity;
import com.listing.user.service.model.dto.UsersEntityDto;
import org.mapstruct.factory.Mappers;

public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UsersEntityDto convertValue(UsersEntity usersEntity);
}
