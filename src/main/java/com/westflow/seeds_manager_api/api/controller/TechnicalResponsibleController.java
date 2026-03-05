package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.TechnicalResponsibleRequest;
import com.westflow.seeds_manager_api.api.dto.request.TechnicalResponsibleUpdateRequest;
import com.westflow.seeds_manager_api.api.dto.response.TechnicalResponsibleResponse;
import com.westflow.seeds_manager_api.api.mapper.TechnicalResponsibleMapper;
import com.westflow.seeds_manager_api.application.usecase.technicalresponsible.RegisterTechnicalResponsibleUseCase;
import com.westflow.seeds_manager_api.application.usecase.technicalresponsible.UpdateTechnicalResponsibleUseCase;
import com.westflow.seeds_manager_api.domain.model.TechnicalResponsible;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
            @Valid @RequestBody TechnicalResponsibleUpdateRequest request
    ) {
        TechnicalResponsible updated = updateTechnicalResponsibleUseCase.execute(id, request);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }
}
