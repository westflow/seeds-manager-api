package com.westflow.seeds_manager_api.application.usecase.company;

import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Company;
import com.westflow.seeds_manager_api.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCompanyUseCase {

    private final CompanyRepository companyRepository;

    public void execute(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa", id));

        company.deactivate();
        companyRepository.save(company);
    }
}
