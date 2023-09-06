package com.listing.user.service.service;

import com.listing.user.service.model.request.UsersRequest;
import com.listing.user.service.entity.EntitiesEntity;
import com.listing.user.service.entity.EntityTypesEntity;
import com.listing.user.service.entity.UsersEntity;

import java.util.List;
import java.util.Optional;

public interface DatabaseService {
    UsersEntity createUser(UsersRequest usersRequest);
    List<UsersEntity> findAll();
    Optional<UsersEntity> findById(int id);
    Optional<UsersEntity> findUserByEmail(String email);
    EntityTypesEntity createEntityType(EntityTypesEntity entityTypesEntity);
    List<EntityTypesEntity> listEntityTypes();
    Optional<EntityTypesEntity> findEntityType(Integer id);
    EntityTypesEntity updateEntityType(EntityTypesEntity entityTypesEntity);
    EntitiesEntity createEntity(EntitiesEntity entitiesEntity);
    List<EntitiesEntity> listEntities();
    Optional<EntitiesEntity> findEntity(Integer id);
    EntitiesEntity updateEntity(EntitiesEntity entitiesEntity);

}
