package com.westflow.seeds_manager_api.application.usecase.bagweight;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.model.BagWeight;
import com.westflow.seeds_manager_api.domain.repository.BagWeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterBagWeightUseCase {

    private final BagWeightRepository bagWeightRepository;

    public BagWeight execute(BagWeight bagWeight) {
        bagWeightRepository.findByWeight(bagWeight.getWeight())
                .ifPresent(existing -> {
                    throw new BusinessException("Já existe um peso de sacaria cadastrado com esse valor.");
                });

        return bagWeightRepository.save(bagWeight);
    }
}
