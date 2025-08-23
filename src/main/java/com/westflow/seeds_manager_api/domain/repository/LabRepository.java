package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.Lab;

import java.util.Optional;

public interface LabRepository {
    Lab save(Lab lab);
    Optional<Lab> findById(Long id);
}
