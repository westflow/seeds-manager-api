package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLotRepository extends JpaRepository<LotEntity,Long> {
}
