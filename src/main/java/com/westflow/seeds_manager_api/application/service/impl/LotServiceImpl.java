package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.LotService;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.repository.LotRepository;
import org.springframework.stereotype.Service;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;

    public LotServiceImpl(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Override
    public Lot register(Lot lot) {
        return lotRepository.save(lot);
    }
}
