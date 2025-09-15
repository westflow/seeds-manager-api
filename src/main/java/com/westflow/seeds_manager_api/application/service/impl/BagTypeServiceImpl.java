package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.BagTypeService;
import com.westflow.seeds_manager_api.domain.entity.BagType;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.BagTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BagTypeServiceImpl implements BagTypeService {

    private final BagTypeRepository bagTypeRepository;

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
        BagType bagType = findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tipo de sacaria", id));

        bagType.deactivate();
        bagTypeRepository.save(bagType);
    }

    @Override
    public Page<BagType> findAll(Pageable pageable) {
        return bagTypeRepository.findAll(pageable);
    }

    @Override
    public BagType update(Long id, BagType bagType) {
        BagType existing = findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tipo de sacaria", id));

        if (!existing.isActive()) {
            throw new IllegalStateException("Tipo de sacaria está inativo e não pode ser atualizado.");
        }

        bagType = BagType.builder()
            .id(existing.getId())
            .name(bagType.getName())
            .build();
        return bagTypeRepository.save(bagType);
    }
}
