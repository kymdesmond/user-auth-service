package com.listing.user.service.repository;

import com.listing.user.service.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UsersEntityRepository extends JpaRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findUsersEntitiesByEmail(String email);
}