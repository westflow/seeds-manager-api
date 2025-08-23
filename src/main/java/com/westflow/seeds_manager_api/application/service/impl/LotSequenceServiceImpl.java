package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.LotSequenceService;
import com.westflow.seeds_manager_api.domain.entity.LotSequence;
import com.westflow.seeds_manager_api.domain.repository.LotSequenceRepository;
import org.springframework.stereotype.Service;

@Service
public class LotSequenceServiceImpl implements LotSequenceService {

    private final LotSequenceRepository repository;

    public LotSequenceServiceImpl(LotSequenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generateNextFormattedNumber() {
        return repository.generateFormattedLotNumber();
    }

    @Override
    public LotSequence resetSequence() {
        return repository.resetPreviousAndCreateCurrent();
    }
}
