package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.entity.Lab;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LabRepository {
    Lab save(Lab lab);
    Optional<Lab> findById(Long id);
    Optional<Lab> findByRenasemCode(String renasemCode);
    Page<Lab> findAll(Pageable pageable);
}
