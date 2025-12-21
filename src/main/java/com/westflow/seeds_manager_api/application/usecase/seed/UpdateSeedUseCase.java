package com.westflow.seeds_manager_api.application.usecase.seed;

import com.westflow.seeds_manager_api.api.dto.request.SeedRequest;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.model.Seed;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateSeedUseCase {

    private final SeedRepository seedRepository;
    private final FindSeedByIdUseCase findSeedByIdUseCase;

    public Seed execute(Long id, SeedRequest request) {
        Seed existing = findSeedByIdUseCase.execute(id);

        if (!Boolean.TRUE.equals(existing.getActive())) {
            throw new BusinessException("Semente está inativa e não pode ser atualizada.");
        }

        Seed candidate = Seed.newSeed(request.getSpecies(), request.getCultivar(), request.isProtected());

        seedRepository.findByNormalizedSpeciesAndNormalizedCultivar(
                        candidate.getNormalizedSpecies(),
                        candidate.getNormalizedCultivar())
                .ifPresent(duplicate -> {
                    if (!duplicate.getId().equals(id)) {
                        throw new BusinessException("Já existe uma semente com essa espécie e cultivar.");
                    }
                });

        existing.update(request.getSpecies(), request.getCultivar(), request.isProtected());

        return seedRepository.save(existing);
    }
}
