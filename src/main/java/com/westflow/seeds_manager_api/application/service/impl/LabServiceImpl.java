package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.LabService;
import com.westflow.seeds_manager_api.domain.entity.Lab;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.LabRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LabServiceImpl implements LabService {

    private final LabRepository labRepository;

    @Override
    public Lab register(Lab lab) {
        labRepository.findByRenasemCode(lab.getRenasemCode())
            .ifPresent(existing -> {
                throw new BusinessException("Já existe um laboratório com esse código RENASEM.");
            });
        return labRepository.save(lab);
    }

    @Override
    public Optional<Lab> findById(Long id) {
        return labRepository.findById(id);
    }

    @Override
    public Page<Lab> findAll(Pageable pageable) {
        return labRepository.findAll(pageable);
    }

    @Override
    public Lab update(Long id, Lab lab) {
        Lab existing = findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Laboratório", id));

        if (!existing.isActive()) {
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

        return labRepository.save(updated);
    }

    @Override
    public void delete(Long id) {
        Lab lab = findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Laboratório", id));

        lab.deactivate();
        labRepository.save(lab);
    }
}
