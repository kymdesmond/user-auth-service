package com.listing.user.service.repository;

import com.listing.user.service.entity.EntitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntitiesEntityRepository extends JpaRepository<EntitiesEntity, Integer> {
}