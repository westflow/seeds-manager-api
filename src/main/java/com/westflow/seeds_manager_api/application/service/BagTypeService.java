package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.BagType;

import java.util.Optional;

public interface BagTypeService {
    BagType register(BagType bagType);
    Optional<BagType> findById(Long id);
}
