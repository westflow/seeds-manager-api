package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.LotReservationService;
import com.westflow.seeds_manager_api.domain.entity.LotReservation;
import com.westflow.seeds_manager_api.domain.repository.LotReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class LotReservationServiceImpl implements LotReservationService {

    private final LotReservationRepository lotReservationRepository;

    public LotReservationServiceImpl(LotReservationRepository lotReservationRepository) {
        this.lotReservationRepository = lotReservationRepository;
    }

    @Override
    public LotReservation reserve(LotReservation reservation) {
        return lotReservationRepository.save(reservation);
    }
}
