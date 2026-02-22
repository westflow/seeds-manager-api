package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.model.TechnicalResponsible;
import com.westflow.seeds_manager_api.domain.repository.TechnicalResponsibleRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.TechnicalResponsibleEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.TechnicalResponsiblePersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaTechnicalResponsibleRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.TechnicalResponsibleSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TechnicalResponsibleRepositoryAdapter implements TechnicalResponsibleRepository {

    private final JpaTechnicalResponsibleRepository jpaRepository;
    private final TechnicalResponsiblePersistenceMapper mapper;

    public TechnicalResponsibleRepositoryAdapter(
            JpaTechnicalResponsibleRepository jpaRepository,
            TechnicalResponsiblePersistenceMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TechnicalResponsible save(TechnicalResponsible technicalResponsible) {
        TechnicalResponsibleEntity entity = mapper.toEntity(technicalResponsible);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<TechnicalResponsible> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<TechnicalResponsible> findByCompanyId(Long companyId, Pageable pageable) {
        Specification<TechnicalResponsibleEntity> spec = TechnicalResponsibleSpecifications.isCompany(companyId)
                .and(TechnicalResponsibleSpecifications.isActive());
        return jpaRepository.findAll(spec, pageable).map(mapper::toDomain);
    }

    @Override
    public Optional<TechnicalResponsible> findPrimaryByCompanyId(Long companyId) {
        return jpaRepository.findByCompanyIdAndIsPrimaryTrue(companyId).map(mapper::toDomain);
    }
}

