package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.LabRequest;
import com.westflow.seeds_manager_api.api.dto.response.LabResponse;
import com.westflow.seeds_manager_api.api.mapper.LabMapper;
import com.westflow.seeds_manager_api.application.service.LabService;
import com.westflow.seeds_manager_api.domain.model.Lab;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.LabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LabServiceImpl implements LabService {

    private final LabRepository labRepository;
    private final LabMapper mapper;

    @Override
    public LabResponse register(LabRequest request) {
        Lab lab = mapper.toDomain(request);
        
        labRepository.findByRenasemCode(lab.getRenasemCode())
            .ifPresent(existing -> {
                throw new BusinessException("Já existe um laboratório com esse código RENASEM.");
            });
            
        Lab saved = labRepository.save(lab);
        return mapper.toResponse(saved);
    }

    @Override
    public LabResponse findById(Long id) {
        return mapper.toResponse(getLabById(id));
    }

    @Override
    public Page<LabResponse> findAll(Pageable pageable) {
        return labRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public LabResponse update(Long id, LabRequest request) {
        Lab lab = mapper.toDomain(request);
        Lab existing = getLabById(id);

        if (!existing.getActive()) {
            throw new BusinessException("Laboratório está inativo e não pode ser atualizado.");
        }

        if (!existing.getRenasemCode().equals(lab.getRenasemCode())) {
            labRepository.findByRenasemCode(lab.getRenasemCode())
                .ifPresent(duplicate -> {
                    throw new BusinessException("Já existe um laboratório cadastrado com esse código RENASEM.");
                });
        }

        Lab updated = Lab.builder()
            .id(existing.getId())
            .name(lab.getName())
            .state(lab.getState())
            .renasemCode(existing.getRenasemCode())
            .createdAt(existing.getCreatedAt())
            .updatedAt(LocalDateTime.now())
            .build();

        Lab saved = labRepository.save(updated);
        return mapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        Lab lab = getLabById(id);
        lab.deactivate();
        labRepository.save(lab);
    }
    
    @Override
    public Optional<Lab> findEntityById(Long id) {
        return labRepository.findById(id);
    }
    
    private Lab getLabById(Long id) {
        return findEntityById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Laboratório", id));
    }
}
