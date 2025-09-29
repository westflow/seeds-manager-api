package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.SeedRequest;
import com.westflow.seeds_manager_api.api.dto.response.SeedResponse;
import com.westflow.seeds_manager_api.api.mapper.SeedMapper;
import com.westflow.seeds_manager_api.application.service.SeedService;
import com.westflow.seeds_manager_api.domain.entity.Seed;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.SeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeedServiceImpl implements SeedService {

    private final SeedRepository seedRepository;
    private final SeedMapper mapper;

    @Override
    public SeedResponse register(SeedRequest request) {
        Seed seed = mapper.toDomain(request);

        Optional<Seed> existing = seedRepository.findByNormalizedSpeciesAndNormalizedCultivar(
                seed.getNormalizedSpecies(), seed.getNormalizedCultivar()
        );
        if (existing.isPresent()) {
            throw new BusinessException("Já existe uma semente com essa espécie e cultivar.");
        }

        Seed saved = seedRepository.save(seed);
        return mapper.toResponse(saved);
    }

    @Override
    public SeedResponse findById(Long id) {
        return mapper.toResponse(getSeedById(id));
    }

    @Override
    public Optional<Seed> findEntityById(Long id) {
        return seedRepository.findById(id);
    }

    @Override
    public Page<SeedResponse> findAll(Boolean isProtected, Pageable pageable) {
        return seedRepository.findAll(isProtected, pageable).map(mapper::toResponse);
    }

    @Override
    public SeedResponse update(Long id, SeedRequest request) {
        Seed existing = getSeedById(id);

        if (!existing.isActive()) {
            throw new BusinessException("Semente está inativa e não pode ser atualizada.");
        }

        Seed seedToUpdate = mapper.toDomain(request);
        
        Optional<Seed> duplicate = seedRepository.findByNormalizedSpeciesAndNormalizedCultivar(
                seedToUpdate.getNormalizedSpecies(), seedToUpdate.getNormalizedCultivar()
        );
        if (duplicate.isPresent() && !duplicate.get().getId().equals(id)) {
            throw new BusinessException("Já existe uma semente com essa espécie e cultivar.");
        }

        existing.update(seedToUpdate);
        Seed updated = seedRepository.save(existing);
        return mapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        Seed seed = getSeedById(id);
        seed.deactivate();
        seedRepository.save(seed);
    }
    
    private Seed getSeedById(Long id) {
        return seedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semente", id));
    }
}
