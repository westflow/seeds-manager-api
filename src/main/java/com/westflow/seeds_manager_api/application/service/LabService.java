package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.Lab;

import java.util.Optional;

public interface LabService {
    Lab register(Lab lab);
    Optional<Lab> findById(Long id);
}
