package com.westflow.seeds_manager_api.application.usecase.company;

import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Company;
import com.westflow.seeds_manager_api.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindCompanyByIdUseCase {

    private final CompanyRepository companyRepository;

    public Company execute(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa", id));
    }
}
