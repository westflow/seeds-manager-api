package com.westflow.seeds_manager_api.application.usecase.technicalresponsible;

import com.westflow.seeds_manager_api.api.dto.request.TechnicalResponsibleUpdateRequest;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import com.westflow.seeds_manager_api.domain.model.TechnicalResponsible;
import com.westflow.seeds_manager_api.domain.repository.TechnicalResponsibleRepository;
import com.westflow.seeds_manager_api.application.support.CurrentUserContext;
import com.westflow.seeds_manager_api.domain.util.CPFUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateTechnicalResponsibleUseCase {

    private final TechnicalResponsibleRepository technicalResponsibleRepository;

    @Transactional
    public TechnicalResponsible execute(Long id, TechnicalResponsibleUpdateRequest request) {
        TechnicalResponsible tr = technicalResponsibleRepository.findByIdAndCompanyId(id, CurrentUserContext.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Responsável técnico", id));

        String normalizedCpf = CPFUtils.normalize(request.getCpf());
        if (request.getCpf() != null) {
            if (normalizedCpf == null || normalizedCpf.length() != 11) {
                throw new ValidationException("CPF inválido");
            }

            Optional<TechnicalResponsible> exist = technicalResponsibleRepository.findByCompanyIdAndCpf(tr.getCompanyId(), normalizedCpf);
            if (exist.isPresent() && !exist.get().getId().equals(tr.getId())) {
                throw new BusinessException("CPF já cadastrado");
            }
        }

        if (Boolean.TRUE.equals(request.getIsPrimary())) {
            technicalResponsibleRepository.findPrimaryByCompanyId(tr.getCompanyId())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(tr.getId())) {
                            existing.unmarkPrimary();
                            technicalResponsibleRepository.save(existing);
                        }
                    });
        }

        tr.update(
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

        return technicalResponsibleRepository.save(tr);
    }
}

