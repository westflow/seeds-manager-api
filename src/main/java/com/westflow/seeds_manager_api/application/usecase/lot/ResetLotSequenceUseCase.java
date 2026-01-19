package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.domain.model.LotSequence;
import com.westflow.seeds_manager_api.domain.repository.LotSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResetLotSequenceUseCase {

    private final LotSequenceRepository lotSequenceRepository;

    public LotSequence execute() {
        return lotSequenceRepository.resetPreviousAndCreateCurrent();
    }
}
