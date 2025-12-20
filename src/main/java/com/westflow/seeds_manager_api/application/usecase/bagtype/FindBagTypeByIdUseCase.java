package com.westflow.seeds_manager_api.application.usecase.bagtype;

import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.BagType;
import com.westflow.seeds_manager_api.domain.repository.BagTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindBagTypeByIdUseCase {

    private final BagTypeRepository bagTypeRepository;

    public BagType execute(Long id) {
        return bagTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de sacaria", id));
    }
}
