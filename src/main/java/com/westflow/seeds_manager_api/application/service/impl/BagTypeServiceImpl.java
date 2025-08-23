package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.BagTypeService;
import com.westflow.seeds_manager_api.domain.entity.BagType;
import com.westflow.seeds_manager_api.domain.repository.BagTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BagTypeServiceImpl implements BagTypeService {

    private final BagTypeRepository bagTypeRepository;

    public BagTypeServiceImpl(BagTypeRepository bagTypeRepository) {
        this.bagTypeRepository = bagTypeRepository;
    }

    @Override
    public BagType register(BagType bagType) {
        return bagTypeRepository.save(bagType);
    }

    @Override
    public Optional<BagType> findById(Long id) {
        return bagTypeRepository.findById(id);
    }
}
