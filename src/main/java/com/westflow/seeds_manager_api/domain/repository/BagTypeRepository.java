package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.BagType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BagTypeRepository {
    BagType save(BagType bagType);
    Optional<BagType> findById(Long id);
    Page<BagType> findAll(Pageable pageable);
}
