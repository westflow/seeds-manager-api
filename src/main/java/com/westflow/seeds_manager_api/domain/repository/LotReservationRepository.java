package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.LotReservation;

import java.util.Optional;

public interface LotReservationRepository {
    LotReservation save(LotReservation lotReservation);
    boolean existsByLotId(Long lotId);
    Optional<LotReservation> findById(Long id);
}
