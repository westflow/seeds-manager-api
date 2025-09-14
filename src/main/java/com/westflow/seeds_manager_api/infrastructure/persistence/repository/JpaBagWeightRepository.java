package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagWeightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.Optional;

public interface JpaBagWeightRepository extends JpaRepository<BagWeightEntity, Long>, JpaSpecificationExecutor<BagWeightEntity> {
    Optional<BagWeightEntity> findByWeight(BigDecimal weight);
}
