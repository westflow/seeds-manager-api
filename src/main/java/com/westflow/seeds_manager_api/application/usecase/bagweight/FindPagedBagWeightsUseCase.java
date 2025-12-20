package com.westflow.seeds_manager_api.application.usecase.bagweight;

import com.westflow.seeds_manager_api.domain.model.BagWeight;
import com.westflow.seeds_manager_api.domain.repository.BagWeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPagedBagWeightsUseCase {

    private final BagWeightRepository bagWeightRepository;

    public Page<BagWeight> execute(Pageable pageable) {
        return bagWeightRepository.findAll(pageable);
    }
}
