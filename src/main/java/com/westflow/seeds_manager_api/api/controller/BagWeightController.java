package com.westflow.seeds_manager_api.api.controller;


import com.westflow.seeds_manager_api.api.dto.request.BagWeightRequest;
import com.westflow.seeds_manager_api.api.dto.response.BagWeightResponse;
import com.westflow.seeds_manager_api.api.mapper.BagWeightMapper;
import com.westflow.seeds_manager_api.application.service.BagWeightService;
import com.westflow.seeds_manager_api.domain.entity.BagWeight;
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
@RequestMapping("/api/bag-weights")
@Tag(name = "BagWeights", description = "Operações de peso de sacaria")
public class BagWeightController {

    private final BagWeightService service;
    private final BagWeightMapper mapper;

    public BagWeightController(BagWeightService service, BagWeightMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Cria novo peso de sacaria", responses = {
            @ApiResponse(responseCode = "201", description = "Peso criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BagWeightResponse> register(@Valid @RequestBody BagWeightRequest request) {
        BagWeight saved = service.register(mapper.toDomain(request));
        BagWeightResponse response = mapper.toResponse(saved);
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
        Page<BagWeightResponse> page = service.findAll(pageable).map(mapper::toResponse);
        return ResponseEntity.ok(page);
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
        return service.findById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
        try {
            BagWeight updated = service.update(id, mapper.toDomain(request, id));
            return ResponseEntity.ok(mapper.toResponse(updated));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
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
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
