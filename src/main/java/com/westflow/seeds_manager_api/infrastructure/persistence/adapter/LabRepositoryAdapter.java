package com.westflow.seeds_manager_api.infrastructure.persistence.adapter;

import com.westflow.seeds_manager_api.domain.entity.Lab;
import com.westflow.seeds_manager_api.domain.repository.LabRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LabEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.LabPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaLabRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.LabSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LabRepositoryAdapter implements LabRepository {

    private final JpaLabRepository jpaRepository;
    private final LabPersistenceMapper mapper;

    public LabRepositoryAdapter(JpaLabRepository jpaRepository, LabPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Lab save(Lab lab) {
        LabEntity entity = mapper.toEntity(lab);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Lab> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Lab> findByRenasemCode(String renasemCode) {
        return jpaRepository.findByRenasemCode(renasemCode)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Lab> findAll(Pageable pageable) {
        Specification<LabEntity> spec = LabSpecifications.isActive();
        return jpaRepository.findAll(spec, pageable)
                .map(mapper::toDomain);
    }
}
