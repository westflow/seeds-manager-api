package com.westflow.seeds_manager_api.application.usecase.technicalresponsible;

import com.westflow.seeds_manager_api.api.dto.request.TechnicalResponsibleRequest;
import com.westflow.seeds_manager_api.application.support.CurrentUserContext;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.model.TechnicalResponsible;
import com.westflow.seeds_manager_api.domain.repository.TechnicalResponsibleRepository;
import com.westflow.seeds_manager_api.domain.util.CPFUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterTechnicalResponsibleUseCase {

    private final TechnicalResponsibleRepository technicalResponsibleRepository;

    public TechnicalResponsible execute(TechnicalResponsibleRequest request, Long optionalCompanyId) {

        Long companyId;
        if (CurrentUserContext.isSuperAdmin() && optionalCompanyId != null) {
            companyId = optionalCompanyId;
        } else {
            companyId = CurrentUserContext.getCompanyId();
        }

        String rawCpf = request.getCpf();
        String normalizedCpf = CPFUtils.normalize(rawCpf);

        if (normalizedCpf != null && technicalResponsibleRepository.findByCompanyIdAndCpf(companyId, normalizedCpf).isPresent()) {
            throw new BusinessException("CPF já cadastrado");
        }

        TechnicalResponsible tr = TechnicalResponsible.newTechnicalResponsible(
                companyId,
                request.getName(),
                normalizedCpf,
                request.getRenasemNumber(),
                request.getCreaNumber(),
                request.getAddress(),
                request.getCity(),
                request.getState(),
                request.getZipCode(),
                request.getPhone(),
                request.getEmail(),
                request.getIsPrimary()
        );

        if (Boolean.TRUE.equals(tr.getIsPrimary())) {
            technicalResponsibleRepository.findPrimaryByCompanyId(companyId)
                    .ifPresent(existing -> {
                        existing.unmarkPrimary();
                        technicalResponsibleRepository.save(existing);
                    });
        }

        return technicalResponsibleRepository.save(tr);
    }
}
