package com.listing.user.service.service;

import com.listing.user.service.entity.EntitiesEntity;
import com.listing.user.service.model.dto.ChangePasswordResponseDto;
import com.listing.user.service.model.request.ChangePasswordRequest;
import com.listing.user.service.model.request.UsersRequest;
import com.listing.user.service.entity.UsersEntity;
import com.listing.user.service.repository.EntitiesEntityRepository;
import com.listing.user.service.repository.UsersEntityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UsersEntityServiceImpl implements UsersEntityService{

    private final EntitiesEntityRepository entitiesEntityRepository;
    private final UsersEntityRepository usersEntityRepository;


    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Override
    public UsersEntity createUser(UsersRequest usersRequest) {
        Optional<EntitiesEntity> optionalEntitiesEntity = entitiesEntityRepository.findById(usersRequest.entityId());
        log.info("Create new user -- {}", usersRequest);
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setFirstName(usersRequest.firstName());
        usersEntity.setLastName(usersRequest.lastName());
        usersEntity.setEmail(usersRequest.email());
        usersEntity.setUsername(usersRequest.username());
        usersEntity.setPhone(usersRequest.phone());
        usersEntity.setPassword(encoder.encode(usersRequest.password()));
        usersEntity.setEntitiesEntity(optionalEntitiesEntity.orElse(null));
        usersEntity.setRoleMapEntityList(Collections.emptyList()); //todo support adding of roles here
        usersEntity.setCreatedBy(usersEntity.getCreatedBy());
        usersEntity.setDateCreated(new Timestamp(new Date().getTime()));
        usersEntity.setDateUpdated(new Timestamp(new Date().getTime()));
        return usersEntityRepository.save(usersEntity);
    }

    @Override
    public UsersEntity updateUser(UsersRequest usersRequest, Integer id) {
        Optional<UsersEntity> optionalUsersEntity =usersEntityRepository.findById(id);
        if (optionalUsersEntity.isEmpty()) {
            // todo throw exception
            log.warn("user not found");
            return null;
        }
        // limit to basic info - use separate function to update email/password/phone with rules
        UsersEntity usersEntity = optionalUsersEntity.get();
        usersEntity.setFirstName(usersRequest.firstName());
        usersEntity.setLastName(usersRequest.lastName());
        usersEntity.setDateUpdated(new Timestamp(new Date().getTime()));
        return usersEntityRepository.save(usersEntity);
    }

    @Override
    public ChangePasswordResponseDto updatePassword(ChangePasswordRequest changePasswordRequest, Integer id) {
        Optional<UsersEntity> optionalUsersEntity =usersEntityRepository.findById(id);
        if (optionalUsersEntity.isEmpty()) {
            log.warn("user not found");
            return new ChangePasswordResponseDto("05", "Invalid user");
        }
        UsersEntity usersEntity = optionalUsersEntity.get();
        if (!changePasswordRequest.newPassword().equals(changePasswordRequest.confirmPassword())) {
            log.warn("new password and confirmation password do not match");
            return new ChangePasswordResponseDto("05", "new password and confirmation password do not match");
        } else if (changePasswordRequest.currentPassword().equals(changePasswordRequest.newPassword())) {
            log.warn("new password should not be the same as current password");
            return new ChangePasswordResponseDto("05", "new password should not be the same as current password");
        } else if (!encoder.matches(changePasswordRequest.currentPassword(), usersEntity.getPassword())) {
            log.warn("current password is invalid");
            return new ChangePasswordResponseDto("05", "current password is invalid");
        }
        usersEntity.setPassword(encoder.encode(changePasswordRequest.newPassword()));
        usersEntity.setDateUpdated(new Timestamp(new Date().getTime()));
        usersEntityRepository.save(usersEntity);
        return new ChangePasswordResponseDto("00", "password change successful");
    }

    @Override
    public List<UsersEntity> findAll() {
        return usersEntityRepository.findAll();
    }

    @Override
    public Optional<UsersEntity> findById(int id) {
        return usersEntityRepository.findById(id);
    }

    @Override
    public Optional<UsersEntity> findUserByEmail(String email) {
        return usersEntityRepository.findUsersEntitiesByEmail(email);
    }
}
