package com.westflow.seeds_manager_api.application.usecase.technicalresponsible;

import com.westflow.seeds_manager_api.application.support.TenantResolver;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.TechnicalResponsible;
import com.westflow.seeds_manager_api.domain.repository.TechnicalResponsibleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteTechnicalResponsibleUseCase {

    private final TechnicalResponsibleRepository technicalResponsibleRepository;

    @Transactional
    public void execute(Long id, Long optionalCompanyId) {
        Long companyId = TenantResolver.resolveCompanyIdOrThrow(optionalCompanyId, "deletar responsável técnico");

        TechnicalResponsible tr = technicalResponsibleRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Responsável técnico", id));

        tr.deactivate();
        technicalResponsibleRepository.save(tr);
    }
}

