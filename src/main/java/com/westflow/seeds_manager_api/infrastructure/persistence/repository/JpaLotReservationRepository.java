package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.domain.enums.LotStatus;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LotReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLotReservationRepository extends JpaRepository<LotReservationEntity,Long> {
    boolean existsByLot_IdAndStatus(Long lotId, LotStatus status);
    Page<LotReservationEntity> findByLot_IdAndStatus(Long lotId, LotStatus status, Pageable pageable);
}
