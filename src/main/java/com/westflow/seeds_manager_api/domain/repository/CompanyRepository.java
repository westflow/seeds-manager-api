package com.westflow.seeds_manager_api.domain.repository;

import com.westflow.seeds_manager_api.domain.model.Company;

import java.util.Optional;

public interface CompanyRepository {

    Company save(Company company);

    boolean existsByTenantCode(String tenantCode);

    boolean existsByCnpj(String cnpj);

    Optional<Company> findById(Long id);
}
