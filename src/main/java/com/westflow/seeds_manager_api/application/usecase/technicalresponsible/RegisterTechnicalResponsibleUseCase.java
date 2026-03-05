package com.westflow.seeds_manager_api.application.usecase.technicalresponsible;

import com.westflow.seeds_manager_api.api.dto.request.TechnicalResponsibleRequest;
import com.westflow.seeds_manager_api.application.support.CurrentUserContext;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.model.TechnicalResponsible;
import com.westflow.seeds_manager_api.domain.repository.TechnicalResponsibleRepository;
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
        String digitsCpf = rawCpf != null ? rawCpf.replaceAll("[^0-9]", "") : null;

        boolean exists = false;
        if (rawCpf != null && technicalResponsibleRepository.findByCompanyIdAndCpf(companyId, rawCpf).isPresent()) {
            exists = true;
        }
        if (!exists && digitsCpf != null && technicalResponsibleRepository.findByCompanyIdAndCpf(companyId, digitsCpf).isPresent()) {
            exists = true;
        }

        if (exists) {
            throw new BusinessException("CPF já cadastrado");
        }

        TechnicalResponsible tr = TechnicalResponsible.newTechnicalResponsible(
                companyId,
                request.getName(),
                request.getCpf(),
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
