package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.LotReservation;

public interface LotReservationRepository {
    LotReservation save(LotReservation lotReservation);
}
