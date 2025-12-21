package com.westflow.seeds_manager_api.application.usecase.seed;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.model.Seed;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterSeedUseCase {

    private final SeedRepository seedRepository;

    public Seed execute(Seed seed) {
        seedRepository.findByNormalizedSpeciesAndNormalizedCultivar(
                        seed.getNormalizedSpecies(),
                        seed.getNormalizedCultivar())
                .ifPresent(existing -> {
                    throw new BusinessException("Já existe uma semente com essa espécie e cultivar.");
                });

        return seedRepository.save(seed);
    }
}
