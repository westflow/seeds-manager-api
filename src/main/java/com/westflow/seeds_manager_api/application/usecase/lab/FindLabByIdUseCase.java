package com.westflow.seeds_manager_api.application.usecase.lab;

import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Lab;
import com.westflow.seeds_manager_api.domain.repository.LabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindLabByIdUseCase {

    private final LabRepository labRepository;

    public Lab execute(Long id) {
        return labRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laboratório", id));
    }
}
