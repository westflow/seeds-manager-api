package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.CompanyCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.CompanyResponse;
import com.westflow.seeds_manager_api.api.mapper.CompanyMapper;
import com.westflow.seeds_manager_api.application.usecase.company.RegisterCompanyUseCase;
import com.westflow.seeds_manager_api.domain.model.Company;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
@Tag(name = "Companies", description = "Operações de empresas (multi-tenant)")
public class CompanyController {

    private final RegisterCompanyUseCase registerCompanyUseCase;
    private final CompanyMapper companyMapper;

    @Operation(
            summary = "Cria uma nova empresa e um usuário OWNER",
            description = "Cria uma empresa (tenant) com validação de tenant_code/CNPJ e cria automaticamente o usuário OWNER associado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos ou conflito de tenant_code/CNPJ/email")
            }
    )
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<CompanyResponse> create(@Valid @RequestBody CompanyCreateRequest request) {
        Company company = Company.newCompany(
                request.getLegalName(),
                request.getTradeName(),
                request.getCnpj(),
                request.getEmail(),
                request.getPhone(),
                request.getAddress(),
                request.getCity(),
                request.getState(),
                request.getZipCode(),
                request.getTenantCode()
        );

        Company saved = registerCompanyUseCase.execute(
                company,
                request.getOwner().getEmail(),
                request.getOwner().getPassword(),
                request.getOwner().getName(),
                request.getOwner().getPosition()
        );

        CompanyResponse response = companyMapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
