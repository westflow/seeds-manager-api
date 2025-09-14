package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.BagWeightService;
import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import com.westflow.seeds_manager_api.domain.repository.BagWeightRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaBagWeightRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagWeightEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.BagWeightSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BagWeightServiceImpl implements BagWeightService {
    private final BagWeightRepository bagWeightRepository;
    private final JpaBagWeightRepository jpaBagWeightRepository;

    public BagWeightServiceImpl(BagWeightRepository bagWeightRepository, JpaBagWeightRepository jpaBagWeightRepository) {
        this.bagWeightRepository = bagWeightRepository;
        this.jpaBagWeightRepository = jpaBagWeightRepository;
    }

    @Override
    public BagWeight register(BagWeight bagWeight) {
        return bagWeightRepository.save(bagWeight);
    }

    @Override
    public Optional<BagWeight> findById(Long id) {
        return bagWeightRepository.findById(id);
    }

    public void delete(Long id) {
        BagWeightEntity entity = jpaBagWeightRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Peso de sacaria não encontrado."));
        if (!entity.isActive()) {
            throw new RuntimeException("Peso de sacaria já está inativo.");
        }
        entity.setActive(false);
        jpaBagWeightRepository.save(entity);
    }

    public Page<BagWeightEntity> findAll(Pageable pageable) {
        Specification<BagWeightEntity> spec = BagWeightSpecifications.isActive();
        return jpaBagWeightRepository.findAll(spec, pageable);
    }
}
