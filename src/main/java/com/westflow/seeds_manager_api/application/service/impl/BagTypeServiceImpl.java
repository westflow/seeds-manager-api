package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.BagTypeService;
import com.westflow.seeds_manager_api.domain.entity.BagType;
import com.westflow.seeds_manager_api.domain.repository.BagTypeRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaBagTypeRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.BagTypeEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.BagTypeSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BagTypeServiceImpl implements BagTypeService {

    private final BagTypeRepository bagTypeRepository;
    private final JpaBagTypeRepository jpaBagTypeRepository;

    public BagTypeServiceImpl(BagTypeRepository bagTypeRepository, JpaBagTypeRepository jpaBagTypeRepository) {
        this.bagTypeRepository = bagTypeRepository;
        this.jpaBagTypeRepository = jpaBagTypeRepository;
    }

    @Override
    public BagType register(BagType bagType) {
        return bagTypeRepository.save(bagType);
    }

    @Override
    public Optional<BagType> findById(Long id) {
        return bagTypeRepository.findById(id);
    }

    public void delete(Long id) {
        BagTypeEntity entity = jpaBagTypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tipo de sacaria não encontrado."));
        if (!entity.isActive()) {
            throw new RuntimeException("Tipo de sacaria já está inativo.");
        }
        entity.setActive(false);
        jpaBagTypeRepository.save(entity);
    }

    public Page<BagTypeEntity> findAll(Pageable pageable) {
        Specification<BagTypeEntity> spec = BagTypeSpecifications.isActive();
        return jpaBagTypeRepository.findAll(spec, pageable);
    }
}
