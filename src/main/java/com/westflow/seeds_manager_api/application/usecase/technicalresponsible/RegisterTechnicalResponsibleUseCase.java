package com.westflow.seeds_manager_api.application.usecase.technicalresponsible;

import com.westflow.seeds_manager_api.domain.model.TechnicalResponsible;
import com.westflow.seeds_manager_api.domain.repository.TechnicalResponsibleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterTechnicalResponsibleUseCase {

    private final TechnicalResponsibleRepository technicalResponsibleRepository;

    public TechnicalResponsible execute(TechnicalResponsible technicalResponsible) {
        if (Boolean.TRUE.equals(technicalResponsible.getIsPrimary())) {
            technicalResponsibleRepository.findPrimaryByCompanyId(technicalResponsible.getCompanyId())
                    .ifPresent(existing -> {
                        existing.unmarkPrimary();
                        technicalResponsibleRepository.save(existing);
                    });
        }

        return technicalResponsibleRepository.save(technicalResponsible);
    }
}

