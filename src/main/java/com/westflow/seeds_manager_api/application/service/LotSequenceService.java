package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.domain.entity.LotSequence;

public interface LotSequenceService {
    String generateNextFormattedNumber();
    LotSequence resetSequence();
}
