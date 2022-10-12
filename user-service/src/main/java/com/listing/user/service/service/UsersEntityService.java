package com.listing.user.service.service;

import com.listing.user.service.model.request.UsersRequest;
import com.listing.user.service.entity.UsersEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UsersEntityService {

    UsersEntity createUser(UsersRequest usersRequest);
    List<UsersEntity> findAll();
    Optional<UsersEntity> findById(Long id);
    Optional<UsersEntity> findUserByEmail(String email);
}
