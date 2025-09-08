package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.Lot;

import java.util.Optional;

public interface LotRepository {
    Lot save(Lot lot);
    Optional<Lot> findById(Long id);
}
