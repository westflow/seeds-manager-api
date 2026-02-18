package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.model.Company;
import com.westflow.seeds_manager_api.domain.repository.CompanyRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.CompanyEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.CompanyPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaCompanyRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CompanyRepositoryAdapter implements CompanyRepository {

    private final JpaCompanyRepository jpaRepository;
    private final CompanyPersistenceMapper mapper;

    public CompanyRepositoryAdapter(JpaCompanyRepository jpaRepository, CompanyPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Company save(Company company) {
        CompanyEntity entity = mapper.toEntity(company);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public boolean existsByTenantCode(String tenantCode) {
        return jpaRepository.existsByTenantCode(tenantCode);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return jpaRepository.existsByCnpj(cnpj);
    }

    @Override
    public Optional<Company> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
