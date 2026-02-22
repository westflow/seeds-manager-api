package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.TechnicalResponsible;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TechnicalResponsibleRepository {
    TechnicalResponsible save(TechnicalResponsible technicalResponsible);
    Optional<TechnicalResponsible> findById(Long id);
    Page<TechnicalResponsible> findByCompanyId(Long companyId, Pageable pageable);
    Optional<TechnicalResponsible> findPrimaryByCompanyId(Long companyId);
}

