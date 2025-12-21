package com.westflow.seeds_manager_api.application.usecase.seed;

import com.westflow.seeds_manager_api.domain.model.Seed;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteSeedUseCase {

    private final SeedRepository seedRepository;
    private final FindSeedByIdUseCase findSeedByIdUseCase;

    public void execute(Long id) {
        Seed seed = findSeedByIdUseCase.execute(id);
        seed.deactivate();
        seedRepository.save(seed);
    }
}
