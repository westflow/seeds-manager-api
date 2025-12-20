package com.westflow.seeds_manager_api.application.usecase.bagweight;

import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.BagWeight;
import com.westflow.seeds_manager_api.domain.repository.BagWeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindBagWeightByIdUseCase {

    private final BagWeightRepository bagWeightRepository;

    public BagWeight execute(Long id) {
        return bagWeightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Peso de sacaria", id));
    }
}
