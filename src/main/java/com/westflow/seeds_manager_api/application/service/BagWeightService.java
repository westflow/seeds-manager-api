package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.BagWeight;

import java.util.Optional;

public interface BagWeightService {
    BagWeight register(BagWeight bagWeight);
    Optional<BagWeight> findById(Long id);
}
