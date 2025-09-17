package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LabEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaLabRepository extends JpaRepository<LabEntity, Long>, JpaSpecificationExecutor<LabEntity> {
    Optional<LabEntity> findByRenasemCode(String renasemCode);
}
