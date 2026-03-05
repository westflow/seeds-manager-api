package com.westflow.seeds_manager_api.infrastructure.persistence.repository;

import com.westflow.seeds_manager_api.infrastructure.persistence.entity.TechnicalResponsibleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaTechnicalResponsibleRepository extends JpaRepository<TechnicalResponsibleEntity, Long>, JpaSpecificationExecutor<TechnicalResponsibleEntity> {
    Optional<TechnicalResponsibleEntity> findByCompanyIdAndIsPrimaryTrue(Long companyId);
    Optional<TechnicalResponsibleEntity> findByCompanyIdAndCpf(Long companyId, String cpf);
}
