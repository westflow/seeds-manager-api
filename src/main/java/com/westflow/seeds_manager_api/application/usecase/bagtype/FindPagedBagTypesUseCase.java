package com.westflow.seeds_manager_api.application.usecase.bagtype;

import com.westflow.seeds_manager_api.domain.model.BagType;
import com.westflow.seeds_manager_api.domain.repository.BagTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPagedBagTypesUseCase {

    private final BagTypeRepository bagTypeRepository;

    public Page<BagType> execute(Pageable pageable) {
        return bagTypeRepository.findAll(pageable);
    }
}
