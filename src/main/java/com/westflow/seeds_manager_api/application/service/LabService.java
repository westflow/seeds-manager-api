package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.LabRequest;
import com.westflow.seeds_manager_api.api.dto.response.LabResponse;
import com.westflow.seeds_manager_api.domain.entity.Lab;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LabService {
    LabResponse register(LabRequest request);
    LabResponse findById(Long id);
    Page<LabResponse> findAll(Pageable pageable);
    Optional<Lab> findEntityById(Long id);
    LabResponse update(Long id, LabRequest request);
    void delete(Long id);
}
