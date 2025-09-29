package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.BagWeightRequest;
import com.westflow.seeds_manager_api.api.dto.response.BagWeightResponse;
import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BagWeightService {
    BagWeightResponse register(BagWeightRequest request);
    BagWeightResponse findById(Long id);
    Optional<BagWeight> findEntityById(Long id);
    Page<BagWeightResponse> findAll(Pageable pageable);
    BagWeightResponse update(Long id, BagWeightRequest request);
    void delete(Long id);
}
