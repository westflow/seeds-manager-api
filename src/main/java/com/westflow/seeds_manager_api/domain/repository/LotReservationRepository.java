package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.LotReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotReservationRepository extends JpaRepository<LotReservation, Long> {
}
