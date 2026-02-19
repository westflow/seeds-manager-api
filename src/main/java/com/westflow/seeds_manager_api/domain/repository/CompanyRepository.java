package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CompanyRepository {

    Company save(Company company);

    boolean existsByTenantCode(String tenantCode);

    boolean existsByCnpj(String cnpj);

    Optional<Company> findById(Long id);

     Page<Company> findAll(Pageable pageable);
}
