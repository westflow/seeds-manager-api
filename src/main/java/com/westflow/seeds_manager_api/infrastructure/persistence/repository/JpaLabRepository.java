package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LabEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLabRepository extends JpaRepository<LabEntity, Long> {
}
