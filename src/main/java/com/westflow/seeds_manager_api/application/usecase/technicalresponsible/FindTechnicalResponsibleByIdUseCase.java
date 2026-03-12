package com.westflow.seeds_manager_api.application.usecase.technicalresponsible;

import com.westflow.seeds_manager_api.api.dto.response.TechnicalResponsibleResponse;
import com.westflow.seeds_manager_api.api.mapper.TechnicalResponsibleMapper;
import com.westflow.seeds_manager_api.application.support.TenantResolver;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.TechnicalResponsibleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindTechnicalResponsibleByIdUseCase {

    private final TechnicalResponsibleRepository technicalResponsibleRepository;
    private final TechnicalResponsibleMapper mapper;

    public TechnicalResponsibleResponse execute(Long id, Long optionalCompanyId) {
        Long companyId = TenantResolver.resolveCompanyIdOrThrow(optionalCompanyId, "buscar responsável técnico");

        return technicalResponsibleRepository.findByIdAndCompanyId(id, companyId)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Responsável técnico", id));
    }
}
