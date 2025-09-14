package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.BagTypeService;
import com.westflow.seeds_manager_api.domain.entity.BagType;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.BagTypeRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaBagTypeRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagTypeEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.BagTypeSpecifications;
import com.westflow.seeds_manager_api.infrastructure.persistence.mapper.BagTypePersistenceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BagTypeServiceImpl implements BagTypeService {

    private final BagTypeRepository bagTypeRepository;
    private final JpaBagTypeRepository jpaBagTypeRepository;
    private final BagTypePersistenceMapper bagTypePersistenceMapper;

    public BagTypeServiceImpl(BagTypeRepository bagTypeRepository, JpaBagTypeRepository jpaBagTypeRepository, BagTypePersistenceMapper bagTypePersistenceMapper) {
        this.bagTypeRepository = bagTypeRepository;
        this.jpaBagTypeRepository = jpaBagTypeRepository;
        this.bagTypePersistenceMapper = bagTypePersistenceMapper;
    }

    @Override
    public BagType register(BagType bagType) {
        return bagTypeRepository.save(bagType);
    }

    @Override
    public Optional<BagType> findById(Long id) {
        return bagTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        BagTypeEntity entity = jpaBagTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tipo de sacaria", id));
        if (!entity.isActive()) {
            throw new BusinessException("Tipo de sacaria já está deletado.");
        }
        entity.setActive(false);
        jpaBagTypeRepository.save(entity);
    }

    @Override
    public Page<BagType> findAll(Pageable pageable) {
        Specification<BagTypeEntity> spec = BagTypeSpecifications.isActive();
        return jpaBagTypeRepository.findAll(spec, pageable)
                .map(bagTypePersistenceMapper::toDomain);
    }

    @Override
    public BagType update(Long id, BagType bagType) {
        BagTypeEntity entity = jpaBagTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tipo de sacaria", id));
        entity.setName(bagType.getName());
        BagTypeEntity updated = jpaBagTypeRepository.save(entity);
        return bagTypePersistenceMapper.toDomain(updated);
    }
}
