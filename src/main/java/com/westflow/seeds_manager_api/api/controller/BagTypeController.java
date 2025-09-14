package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.BagTypeRequest;
import com.westflow.seeds_manager_api.api.dto.response.BagTypeResponse;
import com.westflow.seeds_manager_api.api.mapper.BagTypeMapper;
import com.westflow.seeds_manager_api.application.service.BagTypeService;
import com.westflow.seeds_manager_api.domain.entity.BagType;
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
@RequestMapping("/api/bag-types")
@Tag(name = "BagTypes", description = "Operações de tipo de sacaria")
public class BagTypeController {

    private final BagTypeService service;
    private final BagTypeMapper mapper;

    public BagTypeController(BagTypeService service, BagTypeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Cria novo tipo de sacaria", responses = {
            @ApiResponse(responseCode = "201", description = "Tipo criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BagTypeResponse> register(@Valid @RequestBody BagTypeRequest request) {
        BagType saved = service.register(mapper.toDomain(request));
        BagTypeResponse response = mapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Lista todos os tipos de sacaria",
            description = "Retorna uma lista paginada de tipos de sacaria. Parâmetros de paginação e ordenação: page, size, sort.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping
    public ResponseEntity<Page<BagTypeResponse>> listAll(@ParameterObject Pageable pageable) {
        Page<BagTypeResponse> page = service.findAll(pageable).map(mapper::toResponse);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Busca tipo de sacaria por ID",
            description = "Retorna um tipo de sacaria pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tipo encontrado"),
                    @ApiResponse(responseCode = "404", description = "Tipo não encontrado")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping("/{id}")
    public ResponseEntity<BagTypeResponse> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Atualiza um tipo de sacaria",
            description = "Atualiza os dados de um tipo de sacaria existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tipo atualizado"),
                    @ApiResponse(responseCode = "404", description = "Tipo não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BagTypeResponse> update(@PathVariable Long id, @Valid @RequestBody BagTypeRequest request) {
        try {
            BagType updated = service.update(id, mapper.toDomain(request, id));
            return ResponseEntity.ok(mapper.toResponse(updated));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Remove um tipo de sacaria",
            description = "Remove um tipo de sacaria pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tipo removido"),
                    @ApiResponse(responseCode = "404", description = "Tipo não encontrado")
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
