package com.westflow.seeds_manager_api.application.usecase.bagtype;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.model.BagType;
import com.westflow.seeds_manager_api.domain.repository.BagTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateBagTypeUseCase {

    private final BagTypeRepository bagTypeRepository;
    private final FindBagTypeByIdUseCase findBagTypeByIdUseCase;

    public BagType execute(Long id, String name) {
        BagType existing = findBagTypeByIdUseCase.execute(id);

        if (!Boolean.TRUE.equals(existing.getActive())) {
            throw new BusinessException("Tipo de sacaria está inativo e não pode ser atualizado.");
        }

        existing.update(name);

        return bagTypeRepository.save(existing);
    }
}
