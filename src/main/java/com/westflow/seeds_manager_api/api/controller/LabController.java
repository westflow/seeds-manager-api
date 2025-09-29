package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.LabRequest;
import com.westflow.seeds_manager_api.api.dto.response.LabResponse;
import com.westflow.seeds_manager_api.application.service.LabService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/labs")
@Tag(name = "Labs", description = "Operações de laboratórios credenciados")
public class LabController {

    private final LabService service;

    @Operation(summary = "Cria novo laboratório credenciado", responses = {
            @ApiResponse(responseCode = "201", description = "Laboratório criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LabResponse> register(@Valid @RequestBody LabRequest request) {
        LabResponse response = service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Lista todos os laboratórios",
            description = "Retorna uma lista paginada de laboratórios ativos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping
    public ResponseEntity<Page<LabResponse>> listAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(
            summary = "Busca laboratório por ID",
            description = "Retorna um laboratório ativo pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Laboratório encontrado"),
                    @ApiResponse(responseCode = "404", description = "Laboratório não encontrado")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping("/{id}")
    public ResponseEntity<LabResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Atualiza um laboratório",
            description = "Atualiza os dados de um laboratório existente (exceto o código RENASEM)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Laboratório atualizado"),
                    @ApiResponse(responseCode = "404", description = "Laboratório não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<LabResponse> update(@PathVariable Long id, @Valid @RequestBody LabRequest request) {
        LabResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Remove um laboratório",
            description = "Remove logicamente um laboratório pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Laboratório removido"),
                    @ApiResponse(responseCode = "404", description = "Laboratório não encontrado")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
