package com.westflow.seeds_manager_api.application.usecase.company;

import com.westflow.seeds_manager_api.domain.model.Company;
import com.westflow.seeds_manager_api.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPagedCompaniesUseCase {

    private final CompanyRepository companyRepository;

    public Page<Company> execute(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }
}
