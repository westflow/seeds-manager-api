package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.BagWeightService;
import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.BagWeightRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BagWeightServiceImpl implements BagWeightService {
    private final BagWeightRepository bagWeightRepository;

    @Override
    public BagWeight register(BagWeight bagWeight) {
        bagWeightRepository.findByWeight(bagWeight.getWeight())
            .ifPresent(existing -> {
                throw new BusinessException("Já existe um peso de sacaria cadastrado com esse valor.");
            });
        return bagWeightRepository.save(bagWeight);
    }

    @Override
    public Optional<BagWeight> findById(Long id) {
        return bagWeightRepository.findById(id);
    }

    @Override
    public Page<BagWeight> findAll(Pageable pageable) {
        return bagWeightRepository.findAll(pageable);
    }

    @Override
    public BagWeight update(Long id, BagWeight bagWeight) {
        BagWeight existing = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Peso de sacaria", id));

        if (!existing.isActive()) {
            throw new BusinessException("Peso de sacaria está inativo e não pode ser atualizado.");
        }

        bagWeightRepository.findByWeight(bagWeight.getWeight())
            .filter(b -> !b.getId().equals(id))
            .ifPresent(b -> {
                throw new BusinessException("Já existe um peso de sacaria cadastrado com esse valor.");
            });

        BagWeight updated = BagWeight.builder()
                .id(existing.getId())
                .weight(bagWeight.getWeight())
                .build();
        return bagWeightRepository.save(updated);
    }

    @Override
    public void delete(Long id) {
        BagWeight bagWeight = findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Peso de sacaria", id));

        bagWeight.deactivate();
        bagWeightRepository.save(bagWeight);
    }
}
