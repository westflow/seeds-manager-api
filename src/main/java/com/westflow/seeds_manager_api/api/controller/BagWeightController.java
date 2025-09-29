package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.BagWeightRequest;
import com.westflow.seeds_manager_api.api.dto.response.BagWeightResponse;
import com.westflow.seeds_manager_api.application.service.BagWeightService;
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
@RequestMapping("/api/bag-weights")
@Tag(name = "BagWeights", description = "Operações de peso de sacaria")
public class BagWeightController {

    private final BagWeightService service;

    @Operation(summary = "Cria novo peso de sacaria", responses = {
            @ApiResponse(responseCode = "201", description = "Peso criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BagWeightResponse> register(@Valid @RequestBody BagWeightRequest request) {
        BagWeightResponse response = service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Lista todos os pesos de sacaria",
            description = "Retorna uma lista paginada de pesos de sacaria.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping
    public ResponseEntity<Page<BagWeightResponse>> listAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(
            summary = "Busca peso de sacaria por ID",
            description = "Retorna um peso de sacaria pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Peso encontrado"),
                    @ApiResponse(responseCode = "404", description = "Peso não encontrado")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping("/{id}")
    public ResponseEntity<BagWeightResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Atualiza um peso de sacaria",
            description = "Atualiza os dados de um peso de sacaria existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Peso atualizado"),
                    @ApiResponse(responseCode = "404", description = "Peso não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BagWeightResponse> update(@PathVariable Long id, @Valid @RequestBody BagWeightRequest request) {
        BagWeightResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Remove um peso de sacaria",
            description = "Remove um peso de sacaria pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Peso removido"),
                    @ApiResponse(responseCode = "404", description = "Peso não encontrado")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
