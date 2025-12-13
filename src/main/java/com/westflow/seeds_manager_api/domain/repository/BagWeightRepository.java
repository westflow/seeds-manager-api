package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.BagWeight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

public interface BagWeightRepository {
    BagWeight save(BagWeight bagWeight);
    Optional<BagWeight> findById(Long id);
    Optional<BagWeight> findByWeight(BigDecimal weight);
    Page<BagWeight> findAll(Pageable pageable);
}
