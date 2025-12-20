package com.westflow.seeds_manager_api.application.usecase.lab;

import com.westflow.seeds_manager_api.domain.model.Lab;
import com.westflow.seeds_manager_api.domain.repository.LabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPagedLabsUseCase {

    private final LabRepository labRepository;

    public Page<Lab> execute(Pageable pageable) {
        return labRepository.findAll(pageable);
    }
}
