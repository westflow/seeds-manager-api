package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.BagWeightRequest;
import com.westflow.seeds_manager_api.api.dto.response.BagWeightResponse;
import com.westflow.seeds_manager_api.api.mapper.BagWeightMapper;
import com.westflow.seeds_manager_api.application.service.BagWeightService;
import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.BagWeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BagWeightServiceImpl implements BagWeightService {

    private final BagWeightRepository bagWeightRepository;
    private final BagWeightMapper mapper;

    @Override
    public BagWeightResponse register(BagWeightRequest request) {
        BagWeight bagWeight = mapper.toDomain(request);
        bagWeightRepository.findByWeight(bagWeight.getWeight())
                .ifPresent(existing -> {
                    throw new BusinessException("Já existe um peso de sacaria cadastrado com esse valor.");
                });
        BagWeight saved = bagWeightRepository.save(bagWeight);
        return mapper.toResponse(saved);
    }

    @Override
    public Optional<BagWeight> findEntityById(Long id) {
        return bagWeightRepository.findById(id);
    }

    @Override
    public BagWeightResponse findById(Long id) {
        return mapper.toResponse(getBagWeightById(id));
    }

    @Override
    public BagWeightResponse update(Long id, BagWeightRequest request) {
        BagWeight bagWeight = mapper.toDomain(request);
        BagWeight existing = getBagWeightById(id);

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

        return mapper.toResponse(updated);
    }
    
    @Override
    public Page<BagWeightResponse> findAll(Pageable pageable) {
        return bagWeightRepository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public void delete(Long id) {
        BagWeight bagWeight = getBagWeightById(id);
        bagWeight.deactivate();
        bagWeightRepository.save(bagWeight);
    }
    
    private BagWeight getBagWeightById(Long id) {
        return findEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Peso de sacaria", id));
    }
}
