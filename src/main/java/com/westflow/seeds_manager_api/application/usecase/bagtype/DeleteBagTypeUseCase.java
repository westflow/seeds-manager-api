package com.westflow.seeds_manager_api.application.usecase.bagtype;

import com.westflow.seeds_manager_api.domain.model.BagType;
import com.westflow.seeds_manager_api.domain.repository.BagTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteBagTypeUseCase {

    private final BagTypeRepository bagTypeRepository;
    private final FindBagTypeByIdUseCase findBagTypeByIdUseCase;

    public void execute(Long id) {
        BagType bagType = findBagTypeByIdUseCase.execute(id);
        bagType.deactivate();
        bagTypeRepository.save(bagType);
    }
}
