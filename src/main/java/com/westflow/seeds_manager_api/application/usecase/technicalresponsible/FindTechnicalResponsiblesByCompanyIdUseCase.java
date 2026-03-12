package com.westflow.seeds_manager_api.application.usecase.technicalresponsible;

import com.westflow.seeds_manager_api.api.dto.response.TechnicalResponsibleResponse;
import com.westflow.seeds_manager_api.api.mapper.TechnicalResponsibleMapper;
import com.westflow.seeds_manager_api.domain.repository.TechnicalResponsibleRepository;
import com.westflow.seeds_manager_api.application.support.TenantResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindTechnicalResponsiblesByCompanyIdUseCase {

    private final TechnicalResponsibleRepository technicalResponsibleRepository;
    private final TechnicalResponsibleMapper mapper;

    public Page<TechnicalResponsibleResponse> execute(Long optionalCompanyId, Pageable pageable) {
        Long companyId = TenantResolver.resolveCompanyIdOrThrow(optionalCompanyId, "listar responsáveis técnicos");

        return technicalResponsibleRepository.findByCompanyId(companyId, pageable)
                .map(mapper::toResponse);
    }
}
