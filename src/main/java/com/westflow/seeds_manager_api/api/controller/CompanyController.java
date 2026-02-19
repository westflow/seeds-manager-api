package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.CompanyCreateRequest;
import com.westflow.seeds_manager_api.api.dto.request.CompanyUpdateRequest;
import com.westflow.seeds_manager_api.api.dto.response.CompanyResponse;
import com.westflow.seeds_manager_api.api.mapper.CompanyMapper;
import com.westflow.seeds_manager_api.application.usecase.company.RegisterCompanyUseCase;
import com.westflow.seeds_manager_api.application.usecase.company.UpdateCompanyUseCase;
import com.westflow.seeds_manager_api.application.usecase.company.FindCompanyByIdUseCase;
import com.westflow.seeds_manager_api.application.usecase.company.FindPagedCompaniesUseCase;
import com.westflow.seeds_manager_api.domain.model.Company;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
@Tag(name = "Companies", description = "Operações de empresas (multi-tenant)")
public class CompanyController {

    private final RegisterCompanyUseCase registerCompanyUseCase;
    private final UpdateCompanyUseCase updateCompanyUseCase;
    private final FindCompanyByIdUseCase findCompanyByIdUseCase;
    private final FindPagedCompaniesUseCase findPagedCompaniesUseCase;
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

    @Operation(
            summary = "Lista todas as empresas",
            description = "Retorna uma lista paginada de empresas cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> listAll(@ParameterObject Pageable pageable) {
        Page<Company> page = findPagedCompaniesUseCase.execute(pageable);
        Page<CompanyResponse> response = page.map(companyMapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Busca empresa por ID",
            description = "Retorna os dados de uma empresa a partir do seu identificador",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
                    @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
            }
    )
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getById(@PathVariable Long id) {
        Company company = findCompanyByIdUseCase.execute(id);
        CompanyResponse response = companyMapper.toResponse(company);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Atualiza dados de uma empresa",
            description = "Atualiza dados cadastrais, de contato e branding da empresa (CNPJ e tenant_code são imutáveis)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
            }
    )
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CompanyUpdateRequest request
    ) {
        Company updated = updateCompanyUseCase.execute(
                id,
                request.getLegalName(),
                request.getTradeName(),
                request.getEmail(),
                request.getPhone(),
                request.getAddress(),
                request.getCity(),
                request.getState(),
                request.getZipCode(),
                request.getLogoUrl(),
                request.getPrimaryColor(),
                request.getSecondaryColor()
        );
        CompanyResponse response = companyMapper.toResponse(updated);
        return ResponseEntity.ok(response);
    }
}
