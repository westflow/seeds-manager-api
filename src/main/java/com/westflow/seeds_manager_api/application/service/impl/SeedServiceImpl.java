package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.SeedService;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.repository.JpaSeedRepository;
import com.westflow.seeds_manager_api.infrastructure.persistence.entity.SeedEntity;
import com.westflow.seeds_manager_api.infrastructure.persistence.specification.SeedSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeedServiceImpl implements SeedService {

    private final SeedRepository seedRepository;
    private final JpaSeedRepository jpaSeedRepository;

    public SeedServiceImpl(SeedRepository seedRepository, JpaSeedRepository jpaSeedRepository) {
        this.seedRepository = seedRepository;
        this.jpaSeedRepository = jpaSeedRepository;
    }

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
        Specification<SeedEntity> spec = SeedSpecifications.hasProtected(isProtected);
        return seedRepository.findAll(spec, pageable);
    }

    @Override
    public Seed update(Long id, Seed seed) {
        Seed existing = seedRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Semente não encontrada."));

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
        SeedEntity entity = jpaSeedRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Semente não encontrada."));
        if (!entity.isActive()) {
            throw new BusinessException("Semente já está inativa.");
        }
        entity.setActive(false);
        jpaSeedRepository.save(entity);
    }
}
