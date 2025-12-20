package com.westflow.seeds_manager_api.application.usecase.lab;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.model.Lab;
import com.westflow.seeds_manager_api.domain.repository.LabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateLabUseCase {

    private final LabRepository labRepository;
    private final FindLabByIdUseCase findLabByIdUseCase;

    public Lab execute(Long id, Lab data) {
        Lab existing = findLabByIdUseCase.execute(id);

        if (!Boolean.TRUE.equals(existing.getActive())) {
            throw new BusinessException("Laboratório está inativo e não pode ser atualizado.");
        }

        if (!existing.getRenasemCode().equals(data.getRenasemCode())) {
            labRepository.findByRenasemCode(data.getRenasemCode())
                    .ifPresent(duplicate -> {
                        throw new BusinessException("Já existe um laboratório cadastrado com esse código RENASEM.");
                    });
        }

        existing.update(data.getName(), data.getState(), data.getRenasemCode());

        return labRepository.save(existing);
    }
}
