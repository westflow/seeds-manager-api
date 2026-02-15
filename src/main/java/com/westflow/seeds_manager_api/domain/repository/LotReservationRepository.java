package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.LotReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LotReservationRepository {
    LotReservation save(LotReservation lotReservation);
    boolean existsByLotId(Long lotId);
    Optional<LotReservation> findById(Long id);
    Page<LotReservation> findByLotId(Long lotId, Pageable pageable);
}
