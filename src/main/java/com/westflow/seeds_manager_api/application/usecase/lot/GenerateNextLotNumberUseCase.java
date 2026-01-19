package com.westflow.seeds_manager_api.application.usecase.lot;

import com.westflow.seeds_manager_api.domain.repository.LotSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerateNextLotNumberUseCase {

    private final LotSequenceRepository lotSequenceRepository;

    public String execute() {
        return lotSequenceRepository.generateFormattedLotNumber();
    }
}
