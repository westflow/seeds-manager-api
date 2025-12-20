package com.westflow.seeds_manager_api.application.usecase.lab;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.model.Lab;
import com.westflow.seeds_manager_api.domain.repository.LabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterLabUseCase {

    private final LabRepository labRepository;

    public Lab execute(Lab lab) {
        labRepository.findByRenasemCode(lab.getRenasemCode())
                .ifPresent(existing -> {
                    throw new BusinessException("Já existe um laboratório com esse código RENASEM.");
                });

        return labRepository.save(lab);
    }
}
