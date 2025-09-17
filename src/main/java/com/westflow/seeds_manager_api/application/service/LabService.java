package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.Lab;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LabService {
    Lab register(Lab lab);
    Optional<Lab> findById(Long id);
    Page<Lab> findAll(Pageable pageable);
    Lab update(Long id, Lab lab);
    void delete(Long id);
}
