package com.listing.user.service.repository;

import com.listing.user.service.entity.EntityTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityTypesEntityRepository extends JpaRepository<EntityTypesEntity, Integer> {
}