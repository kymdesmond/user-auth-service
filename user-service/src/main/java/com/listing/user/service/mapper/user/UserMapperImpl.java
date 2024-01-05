package com.listing.user.service.mapper.user;

import com.listing.user.service.entity.UsersEntity;
import com.listing.user.service.model.dto.UsersEntityDto;

public class UserMapperImpl implements UserMapper{
    @Override
    public UsersEntityDto convertValue(UsersEntity usersEntity) {
        return new UsersEntityDto(
                usersEntity.getId(),
                usersEntity.getFirstName(),
                usersEntity.getLastName(),
                usersEntity.getUsername(),
                usersEntity.getPhone(),
                usersEntity.getEmail(),
                usersEntity.getEntitiesEntity() != null ? usersEntity.getEntitiesEntity().getName() : "",
                usersEntity.getEntitiesEntity() != null ? usersEntity.getEntitiesEntity().getId() : 0,
                usersEntity.getRoleMapEntityList()
                        .stream()
                        .map(roleMapEntity -> roleMapEntity.getRolesEntity().getName())
                        .toList(),
                usersEntity.getDateCreated(),
                usersEntity.getDateUpdated()
        );
    }
}
