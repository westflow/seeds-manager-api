package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaBagTypeRepository extends JpaRepository<BagTypeEntity, Long>, JpaSpecificationExecutor<BagTypeEntity> {
}
