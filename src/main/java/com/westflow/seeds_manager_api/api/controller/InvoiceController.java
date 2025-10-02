package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.InvoiceRequest;
import com.westflow.seeds_manager_api.api.dto.response.InvoiceResponse;
import com.westflow.seeds_manager_api.application.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invoices")
@Tag(name = "Invoices", description = "Operações de notas fiscais")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Operation(
            summary = "Cria uma nova nota fiscal",
            description = "Valida e registra uma nota fiscal no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Nota fiscal criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "409", description = "Número de nota fiscal já existe")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<InvoiceResponse> create(@Valid @RequestBody InvoiceRequest request) {
        InvoiceResponse response = invoiceService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Lista todas as notas fiscais",
            description = "Retorna uma lista paginada de notas fiscais ativas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de notas fiscais retornada com sucesso")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping
    public ResponseEntity<Page<InvoiceResponse>> listAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(invoiceService.findAll(pageable));
    }

    @Operation(
            summary = "Busca nota fiscal por ID",
            description = "Retorna uma nota fiscal específica pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Nota fiscal encontrada"),
                    @ApiResponse(responseCode = "404", description = "Nota fiscal não encontrada")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getById(
            @Parameter(description = "ID da nota fiscal", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.findById(id));
    }

    @Operation(
            summary = "Atualiza uma nota fiscal",
            description = "Atualiza os dados de uma nota fiscal existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Nota fiscal atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Nota fiscal não encontrada"),
                    @ApiResponse(responseCode = "409", description = "Nota fiscal está inativa")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponse> update(
            @Parameter(description = "ID da nota fiscal", required = true)
            @PathVariable Long id,
            @Valid @RequestBody InvoiceRequest request) {
        return ResponseEntity.ok(invoiceService.update(id, request));
    }

    @Operation(
            summary = "Remove uma nota fiscal",
            description = "Remove logicamente uma nota fiscal pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Nota fiscal removida com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Nota fiscal não encontrada")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da nota fiscal", required = true)
            @PathVariable Long id) {
        invoiceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}