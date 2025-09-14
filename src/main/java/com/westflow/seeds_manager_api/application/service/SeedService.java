package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.Seed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SeedService {
    Seed register(Seed seed);
    Optional<Seed> findById(Long id);
    Page<Seed> findAll(Boolean isProtected, Pageable pageable);
    Seed update(Long id, Seed seed);
    void delete(Long id);
}
