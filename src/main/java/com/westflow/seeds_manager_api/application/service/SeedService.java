package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.SeedRequest;
import com.westflow.seeds_manager_api.api.dto.response.SeedResponse;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SeedService {
    SeedResponse register(SeedRequest request);
    SeedResponse findById(Long id);
    Seed findEntityById(Long id);
    Page<SeedResponse> findAll(Boolean isProtected, Pageable pageable);
    SeedResponse update(Long id, SeedRequest request);
    void delete(Long id);
}
