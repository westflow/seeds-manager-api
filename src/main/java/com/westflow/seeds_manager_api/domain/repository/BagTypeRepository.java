package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.BagType;

import java.util.Optional;

public interface BagTypeRepository {
    BagType save(BagType bagType);
    Optional<BagType> findById(Long id);
}
