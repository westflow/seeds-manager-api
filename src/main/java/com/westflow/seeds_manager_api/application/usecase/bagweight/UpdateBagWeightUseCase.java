package com.westflow.seeds_manager_api.application.usecase.bagweight;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.model.BagWeight;
import com.westflow.seeds_manager_api.domain.repository.BagWeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateBagWeightUseCase {

    private final BagWeightRepository bagWeightRepository;
    private final FindBagWeightByIdUseCase findBagWeightByIdUseCase;

    public BagWeight execute(Long id, BagWeight data) {
        BagWeight existing = findBagWeightByIdUseCase.execute(id);

        if (!Boolean.TRUE.equals(existing.getActive())) {
            throw new BusinessException("Peso de sacaria está inativo e não pode ser atualizado.");
        }

        bagWeightRepository.findByWeight(data.getWeight())
                .filter(b -> !b.getId().equals(id))
                .ifPresent(b -> {
                    throw new BusinessException("Já existe um peso de sacaria cadastrado com esse valor.");
                });

        existing.update(data.getWeight());

        return bagWeightRepository.save(existing);
    }
}
