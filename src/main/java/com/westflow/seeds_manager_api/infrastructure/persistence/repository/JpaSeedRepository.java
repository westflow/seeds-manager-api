package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.SeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSeedRepository extends JpaRepository<SeedEntity, Long> {
}
