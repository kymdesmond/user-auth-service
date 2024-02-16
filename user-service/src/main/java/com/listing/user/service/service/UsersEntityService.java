package com.listing.user.service.service;

import com.listing.user.service.model.dto.ChangePasswordResponseDto;
import com.listing.user.service.model.request.ChangePasswordRequest;
import com.listing.user.service.model.request.UsersRequest;
import com.listing.user.service.entity.UsersEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UsersEntityService {

    UsersEntity createUser(UsersRequest usersRequest);
    UsersEntity updateUser(UsersRequest usersRequest, Integer id);
    ChangePasswordResponseDto updatePassword(ChangePasswordRequest changePasswordRequest, Integer id);
    List<UsersEntity> findAll();
    Optional<UsersEntity> findById(int id);
    Optional<UsersEntity> findUserByEmail(String email);
}
