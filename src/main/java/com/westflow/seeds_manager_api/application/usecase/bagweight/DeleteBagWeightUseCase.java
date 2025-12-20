package com.westflow.seeds_manager_api.application.usecase.bagweight;

import com.westflow.seeds_manager_api.domain.model.BagWeight;
import com.westflow.seeds_manager_api.domain.repository.BagWeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteBagWeightUseCase {

    private final BagWeightRepository bagWeightRepository;
    private final FindBagWeightByIdUseCase findBagWeightByIdUseCase;

    public void execute(Long id) {
        BagWeight bagWeight = findBagWeightByIdUseCase.execute(id);
        bagWeight.deactivate();
        bagWeightRepository.save(bagWeight);
    }
}
