package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.BagType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BagTypeService {
    BagType register(BagType bagType);
    Optional<BagType> findById(Long id);
    Page<BagType> findAll(Pageable pageable);
    BagType update(Long id, BagType bagType);
    void delete(Long id);
}
