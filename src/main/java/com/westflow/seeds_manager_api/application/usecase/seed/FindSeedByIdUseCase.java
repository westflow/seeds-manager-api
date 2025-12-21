package com.westflow.seeds_manager_api.application.usecase.seed;

import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Seed;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindSeedByIdUseCase {

    private final SeedRepository seedRepository;

    public Seed execute(Long id) {
        return seedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semente", id));
    }
}
