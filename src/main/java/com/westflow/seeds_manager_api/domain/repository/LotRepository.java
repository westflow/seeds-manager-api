package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.Lot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LotRepository {
    Lot save(Lot lot);
    Optional<Lot> findById(Long id);
    Page<Lot> findAll(Pageable pageable);
}
