package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.BagWeight;

import java.util.Optional;

public interface BagWeightRepository {
    BagWeight save(BagWeight bagWeight);
    Optional<BagWeight> findById(Long id);
}
