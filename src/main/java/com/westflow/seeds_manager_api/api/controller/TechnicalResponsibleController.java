package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.TechnicalResponsibleRequest;
import com.westflow.seeds_manager_api.api.dto.response.TechnicalResponsibleResponse;
import com.westflow.seeds_manager_api.api.mapper.TechnicalResponsibleMapper;
import com.westflow.seeds_manager_api.application.usecase.technicalresponsible.RegisterTechnicalResponsibleUseCase;
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

    @Operation(summary = "Cria um responsável técnico")
    @ApiResponse(responseCode = "201", description = "Responsável criado")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TechnicalResponsibleResponse> create(@Valid @RequestBody TechnicalResponsibleRequest request) {
        TechnicalResponsible tr = TechnicalResponsible.newTechnicalResponsible(
                request.getCompanyId(),
                request.getName(),
                request.getCpf(),
                request.getRenasemNumber(),
                request.getCreaNumber(),
                request.getAddress(),
                request.getCity(),
                request.getState(),
                request.getZipCode(),
                request.getPhone(),
                request.getEmail(),
                request.getIsPrimary()
        );

        TechnicalResponsible saved = registerTechnicalResponsibleUseCase.execute(tr);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }
}

