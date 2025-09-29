package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.BagTypeRequest;
import com.westflow.seeds_manager_api.api.dto.response.BagTypeResponse;
import com.westflow.seeds_manager_api.domain.entity.BagType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BagTypeService {
    BagTypeResponse register(BagTypeRequest request);
    BagTypeResponse findById(Long id);
    BagType findEntityById(Long id);
    Page<BagTypeResponse> findAll(Pageable pageable);
    BagTypeResponse update(Long id, BagTypeRequest request);
    void delete(Long id);
}
