package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.TechnicalResponsibleRequest;
import com.westflow.seeds_manager_api.api.dto.request.TechnicalResponsibleUpdateRequest;
import com.westflow.seeds_manager_api.api.dto.response.TechnicalResponsibleResponse;
import com.westflow.seeds_manager_api.api.mapper.TechnicalResponsibleMapper;
import com.westflow.seeds_manager_api.application.usecase.technicalresponsible.RegisterTechnicalResponsibleUseCase;
import com.westflow.seeds_manager_api.application.usecase.technicalresponsible.UpdateTechnicalResponsibleUseCase;
import com.westflow.seeds_manager_api.application.usecase.technicalresponsible.FindTechnicalResponsiblesByCompanyIdUseCase;
import com.westflow.seeds_manager_api.application.usecase.technicalresponsible.FindTechnicalResponsibleByIdUseCase;
import com.westflow.seeds_manager_api.domain.model.TechnicalResponsible;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/technical-responsibles")
@Tag(name = "TechnicalResponsibles", description = "Operações de responsáveis técnicos")
public class TechnicalResponsibleController {

    private final TechnicalResponsibleMapper mapper;
    private final RegisterTechnicalResponsibleUseCase registerTechnicalResponsibleUseCase;
    private final UpdateTechnicalResponsibleUseCase updateTechnicalResponsibleUseCase;
    private final FindTechnicalResponsiblesByCompanyIdUseCase findTechnicalResponsiblesByCompanyIdUseCase;
    private final FindTechnicalResponsibleByIdUseCase findTechnicalResponsibleByIdUseCase;

    @Operation(summary = "Cria um responsável técnico")
    @ApiResponse(responseCode = "201", description = "Responsável criado")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN','SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<TechnicalResponsibleResponse> create(
            @Valid @RequestBody TechnicalResponsibleRequest request,
            @RequestParam(value = "companyId", required = false) Long companyId
    ) {
        TechnicalResponsible saved = registerTechnicalResponsibleUseCase.execute(request, companyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @Operation(summary = "Atualiza um responsável técnico")
    @ApiResponse(responseCode = "200", description = "Responsável atualizado")
    @PreAuthorize("hasAnyRole('OWNER','ADMIN','SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TechnicalResponsibleResponse> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody TechnicalResponsibleUpdateRequest request,
            @RequestParam(value = "companyId", required = false) Long companyId
    ) {
        TechnicalResponsible updated = updateTechnicalResponsibleUseCase.execute(id, request, companyId);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @Operation(summary = "Busca um responsável técnico por id")
    @ApiResponse(responseCode = "200", description = "Responsável encontrado")
    @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
    @PreAuthorize("hasAnyRole('OWNER','ADMIN','SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<TechnicalResponsibleResponse> findById(
            @PathVariable("id") Long id,
            @RequestParam(value = "companyId", required = false) Long companyId
    ) {
        TechnicalResponsibleResponse response = findTechnicalResponsibleByIdUseCase.execute(id, companyId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Lista responsáveis técnicos ativos da empresa")
    @ApiResponse(responseCode = "200", description = "Lista paginada de responsáveis")
    @PreAuthorize("hasAnyRole('OWNER','ADMIN','SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<TechnicalResponsibleResponse>> listAll(
            @ParameterObject Pageable pageable,
            @RequestParam(value = "companyId", required = false) Long companyId
    ) {
        Page<TechnicalResponsibleResponse> page = findTechnicalResponsiblesByCompanyIdUseCase.execute(companyId, pageable);
        return ResponseEntity.ok(page);
    }
}
