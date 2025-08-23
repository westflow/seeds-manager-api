package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.BagWeightService;
import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import com.westflow.seeds_manager_api.domain.repository.BagWeightRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BagWeightServiceImpl implements BagWeightService {
    private final BagWeightRepository bagWeightRepository;

    public BagWeightServiceImpl(BagWeightRepository bagWeightRepository) {
        this.bagWeightRepository = bagWeightRepository;
    }

    @Override
    public BagWeight register(BagWeight bagWeight) {
        return bagWeightRepository.save(bagWeight);
    }

    @Override
    public Optional<BagWeight> findById(Long id) {
        return bagWeightRepository.findById(id);
    }
}
