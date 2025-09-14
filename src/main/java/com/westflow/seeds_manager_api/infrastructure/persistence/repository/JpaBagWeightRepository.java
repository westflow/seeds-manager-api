package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagWeightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaBagWeightRepository extends JpaRepository<BagWeightEntity, Long>, JpaSpecificationExecutor<BagWeightEntity> {
}
