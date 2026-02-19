package com.westflow.seeds_manager_api.application.usecase.company;

import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.model.Company;
import com.westflow.seeds_manager_api.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCompanyUseCase {

    private final CompanyRepository companyRepository;

    public Company execute(Long id,
                           String legalName,
                           String tradeName,
                           String email,
                           String phone,
                           String address,
                           String city,
                           String state,
                           String zipCode,
                           String logoUrl,
                           String primaryColor,
                           String secondaryColor) {

        Company existing = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa", id));

        existing.update(
                legalName,
                tradeName,
                email,
                phone,
                address,
                city,
                state,
                zipCode,
                logoUrl,
                primaryColor,
                secondaryColor
        );

        return companyRepository.save(existing);
    }
}
