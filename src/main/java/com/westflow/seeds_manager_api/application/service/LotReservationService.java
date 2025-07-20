package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.LotReservation;

public interface LotReservationService {
    LotReservation reserve(LotReservation reservation);
}
