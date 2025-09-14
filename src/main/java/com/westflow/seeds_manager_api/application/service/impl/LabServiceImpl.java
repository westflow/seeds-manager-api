package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.LabService;
import com.westflow.seeds_manager_api.domain.entity.Lab;
import com.westflow.seeds_manager_api.domain.repository.LabRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaLabRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.LabEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.LabSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LabServiceImpl implements LabService {

    private final LabRepository labRepository;
    private final JpaLabRepository jpaLabRepository;

    public LabServiceImpl(LabRepository labRepository, JpaLabRepository jpaLabRepository) {
        this.labRepository = labRepository;
        this.jpaLabRepository = jpaLabRepository;
    }

    @Override
    public Lab register(Lab lab) {
        return labRepository.save(lab);
    }

    @Override
    public Optional<Lab> findById(Long id) {
        return labRepository.findById(id);
    }

    public void delete(Long id) {
        LabEntity entity = jpaLabRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Laboratório não encontrado."));
        if (!entity.isActive()) {
            throw new RuntimeException("Laboratório já está inativo.");
        }
        entity.setActive(false);
        jpaLabRepository.save(entity);
    }

    public Page<LabEntity> findAll(Pageable pageable) {
        Specification<LabEntity> spec = LabSpecifications.isActive();
        return jpaLabRepository.findAll(spec, pageable);
    }
}
