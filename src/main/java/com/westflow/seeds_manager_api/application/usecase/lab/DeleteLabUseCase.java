package com.westflow.seeds_manager_api.application.usecase.lab;

import com.westflow.seeds_manager_api.domain.model.Lab;
import com.westflow.seeds_manager_api.domain.repository.LabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteLabUseCase {

    private final LabRepository labRepository;
    private final FindLabByIdUseCase findLabByIdUseCase;

    public void execute(Long id) {
        Lab lab = findLabByIdUseCase.execute(id);
        lab.deactivate();
        labRepository.save(lab);
    }
}
