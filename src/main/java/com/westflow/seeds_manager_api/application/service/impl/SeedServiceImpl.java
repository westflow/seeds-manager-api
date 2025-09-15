package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.SeedService;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SeedServiceImpl implements SeedService {

    private final SeedRepository seedRepository;

    @Override
    public Seed register(Seed seed) {

        Optional<Seed> existing = seedRepository.findByNormalizedSpeciesAndNormalizedCultivar(
                seed.getNormalizedSpecies(), seed.getNormalizedCultivar()
        );
        if (existing.isPresent()) {
            throw new BusinessException("Já existe uma semente com essa espécie e cultivar.");
        }

        return seedRepository.save(seed);
    }

    @Override
    public Optional<Seed> findById(Long id) {
        return seedRepository.findById(id);
    }

    @Override
    public Page<Seed> findAll(Boolean isProtected, Pageable pageable) {
        return seedRepository.findAll(isProtected, pageable);
    }

    @Override
    public Seed update(Long id, Seed seed) {
        Seed existing = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semente", id));

        if (!existing.isActive()) {
            throw new BusinessException("Semente está inativa e não pode ser atualizada.");
        }

        Optional<Seed> duplicate = seedRepository.findByNormalizedSpeciesAndNormalizedCultivar(
                seed.getNormalizedSpecies(), seed.getNormalizedCultivar()
        );
        if (duplicate.isPresent() && !duplicate.get().getId().equals(id)) {
            throw new BusinessException("Já existe uma semente com essa espécie e cultivar.");
        }

        Seed updated = Seed.builder()
                .id(existing.getId())
                .species(seed.getSpecies())
                .cultivar(seed.getCultivar())
                .isProtected(seed.isProtected())
                .build();
        return seedRepository.save(updated);
    }

    @Override
    public void delete(Long id) {
        Seed seed = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semente", id));

        seed.deactivate();
        seedRepository.save(seed);
    }
}
