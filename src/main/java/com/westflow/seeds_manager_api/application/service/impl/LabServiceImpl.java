package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.application.service.LabService;
import com.westflow.seeds_manager_api.domain.entity.Lab;
import com.westflow.seeds_manager_api.domain.repository.LabRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LabServiceImpl implements LabService {

    private final LabRepository labRepository;

    public LabServiceImpl(LabRepository labRepository) {
        this.labRepository = labRepository;
    }

    @Override
    public Lab register(Lab lab) {
        return labRepository.save(lab);
    }

    @Override
    public Optional<Lab> findById(Long id) {
        return labRepository.findById(id);
    }
}
