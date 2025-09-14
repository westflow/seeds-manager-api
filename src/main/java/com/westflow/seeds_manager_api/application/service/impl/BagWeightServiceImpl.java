package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.BagWeightService;
import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import com.westflow.seeds_manager_api.domain.repository.BagWeightRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaBagWeightRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagWeightEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.BagWeightPersistenceMapper;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.BagWeightSpecifications;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BagWeightServiceImpl implements BagWeightService {
    private final BagWeightRepository bagWeightRepository;
    private final JpaBagWeightRepository jpaBagWeightRepository;
    private final BagWeightPersistenceMapper bagWeightPersistenceMapper;

    public BagWeightServiceImpl(BagWeightRepository bagWeightRepository, JpaBagWeightRepository jpaBagWeightRepository, BagWeightPersistenceMapper bagWeightPersistenceMapper) {
        this.bagWeightRepository = bagWeightRepository;
        this.jpaBagWeightRepository = jpaBagWeightRepository;
        this.bagWeightPersistenceMapper = bagWeightPersistenceMapper;
    }

    @Override
    public BagWeight register(BagWeight bagWeight) {
        jpaBagWeightRepository.findByWeight(bagWeight.getWeight())
            .ifPresent(existing -> { throw new BusinessException("Já existe um peso de sacaria cadastrado com esse valor."); });
        return bagWeightRepository.save(bagWeight);
    }

    @Override
    public Optional<BagWeight> findById(Long id) {
        return bagWeightRepository.findById(id);
    }

    @Override
    public Page<BagWeight> findAll(Pageable pageable) {
        Specification<BagWeightEntity> spec = BagWeightSpecifications.isActive();
        return jpaBagWeightRepository.findAll(spec, pageable)
                .map(bagWeightPersistenceMapper::toDomain);
    }

    @Override
    public BagWeight update(Long id, BagWeight bagWeight) {
        BagWeightEntity entity = jpaBagWeightRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Peso de sacaria", id));
        jpaBagWeightRepository.findByWeight(bagWeight.getWeight())
            .filter(existing -> !existing.getId().equals(id))
            .ifPresent(existing -> { throw new BusinessException("Já existe um peso de sacaria cadastrado com esse valor."); });
        entity.setWeight(bagWeight.getWeight());
        BagWeightEntity updated = jpaBagWeightRepository.save(entity);
        return bagWeightPersistenceMapper.toDomain(updated);
    }

    @Override
    public void delete(Long id) {
        BagWeightEntity entity = jpaBagWeightRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Peso de sacaria", id));
        if (!entity.isActive()) {
            throw new BusinessException("Peso de sacaria já está inativo.");
        }
        entity.setActive(false);
        jpaBagWeightRepository.save(entity);
    }
}
