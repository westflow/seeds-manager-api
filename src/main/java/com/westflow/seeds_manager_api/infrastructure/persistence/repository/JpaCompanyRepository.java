package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompanyRepository extends JpaRepository<CompanyEntity, Long> {

    boolean existsByTenantCode(String tenantCode);

    boolean existsByCnpj(String cnpj);

    Page<CompanyEntity> findAllByActiveTrue(Pageable pageable);
}
