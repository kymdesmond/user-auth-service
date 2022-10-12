package com.listing.user.service.service;

import com.listing.user.service.model.request.UsersRequest;
import com.listing.user.service.entity.EntitiesEntity;
import com.listing.user.service.entity.EntityTypesEntity;
import com.listing.user.service.entity.UsersEntity;
import com.listing.user.service.repository.EntitiesEntityRepository;
import com.listing.user.service.repository.EntityTypesEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DatabaseServiceImpl implements DatabaseService{

    @Autowired
    UsersEntityService usersEntityService;

    @Autowired
    EntitiesEntityRepository entitiesEntityRepository;

    @Autowired
    EntityTypesEntityRepository entityTypesEntityRepository;

    @Override
    public UsersEntity createUser(UsersRequest usersRequest) {
        return usersEntityService.createUser(usersRequest);
    }

    @Override
    public List<UsersEntity> findAll() {
        return usersEntityService.findAll();
    }

    @Override
    public Optional<UsersEntity> findById(Long id) {
        return usersEntityService.findById(id);
    }

    @Override
    public Optional<UsersEntity> findUserByEmail(String email) {
        return usersEntityService.findUserByEmail(email);
    }

    @Override
    public EntityTypesEntity createEntityType(EntityTypesEntity entityTypesEntity) {
        return entityTypesEntityRepository.save(entityTypesEntity);
    }

    @Override
    public List<EntityTypesEntity> listEntityTypes() {
        return entityTypesEntityRepository.findAll();
    }

    @Override
    public Optional<EntityTypesEntity> findEntityType(Integer id) {
        return entityTypesEntityRepository.findById(id);
    }

    @Override
    public EntityTypesEntity updateEntityType(EntityTypesEntity entityTypesEntity) {
        return entityTypesEntityRepository.save(entityTypesEntity);
    }

    @Override
    public EntitiesEntity createEntity(EntitiesEntity entitiesEntity) {
        return entitiesEntityRepository.save(entitiesEntity);
    }

    @Override
    public List<EntitiesEntity> listEntities() {
        return entitiesEntityRepository.findAll();
    }

    @Override
    public Optional<EntitiesEntity> findEntity(Integer id) {
        return entitiesEntityRepository.findById(id);
    }

    @Override
    public EntitiesEntity updateEntity(EntitiesEntity entitiesEntity) {
        return entitiesEntityRepository.save(entitiesEntity);
    }
}
