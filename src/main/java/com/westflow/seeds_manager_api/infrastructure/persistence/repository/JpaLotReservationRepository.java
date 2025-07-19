package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLotReservationRepository extends JpaRepository<LotReservationEntity,Long> {
}
