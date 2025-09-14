package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BagWeightService {
    BagWeight register(BagWeight bagWeight);
    Optional<BagWeight> findById(Long id);
    Page<BagWeight> findAll(Pageable pageable);
    BagWeight update(Long id, BagWeight bagWeight);
    void delete(Long id);
}
