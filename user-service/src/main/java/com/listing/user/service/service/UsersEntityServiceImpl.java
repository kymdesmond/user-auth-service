package com.listing.user.service.service;

import com.listing.user.service.model.request.UsersRequest;
import com.listing.user.service.entity.UsersEntity;
import com.listing.user.service.repository.UsersEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UsersEntityServiceImpl implements UsersEntityService{

    UsersEntityRepository usersEntityRepository;

    public  UsersEntityServiceImpl(UsersEntityRepository usersEntityRepository) {
        this.usersEntityRepository = usersEntityRepository;
    }

    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Override
    public UsersEntity createUser(UsersRequest usersRequest) {
        log.info("Create new user -- {}", usersRequest);
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setFirstName(usersRequest.firstName());
        usersEntity.setLastName(usersRequest.lastName());
        usersEntity.setEmail(usersRequest.email());
        usersEntity.setUsername(usersRequest.username());
        usersEntity.setPhone(usersRequest.phone());
        usersEntity.setPassword(encoder.encode(usersRequest.password()));
        usersEntity.setDateCreated(new Timestamp(new Date().getTime()));
        usersEntity.setDateUpdated(new Timestamp(new Date().getTime()));
        return usersEntityRepository.save(usersEntity);
    }

    @Override
    public List<UsersEntity> findAll() {
        return usersEntityRepository.findAll();
    }

    @Override
    public Optional<UsersEntity> findById(Long id) {
        return usersEntityRepository.findById(id);
    }

    @Override
    public Optional<UsersEntity> findUserByEmail(String email) {
        return usersEntityRepository.findUsersEntitiesByEmail(email);
    }
}
