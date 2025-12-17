package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.config.CurrentUser;
import com.westflow.seeds_manager_api.api.dto.request.LotRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.api.mapper.LotMapper;
import com.westflow.seeds_manager_api.application.service.LotInvoiceService;
import com.westflow.seeds_manager_api.application.usecase.lot.*;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.User;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lots")
@Tag(name = "Lots", description = "Operações de lotes")
public class LotController {

    private final CreateLotUseCase createLotUseCase;
    private final UpdateLotUseCase updateLotUseCase;
    private final DeleteLotUseCase deleteLotUseCase;
    private final FindLotByIdUseCase findLotByIdUseCase;
    private final FindPagedLotsUseCase findPagedLotsUseCase;
    private final LotMapper lotMapper;
    private final LotInvoiceService lotInvoiceService;

    @Operation(
            summary = "Cria um novo lote",
            description = "Valida e registra um novo lote vinculado ao usuário logado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Lote criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LotResponse> create(@Valid @RequestBody LotRequest request,
                                              @Parameter(hidden = true) @CurrentUser User user) {
        LotResponse response = createLotUseCase.execute(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Atualiza um lote",
            description = "Valida e atualiza um lote existente vinculado ao usuário logado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lote atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Lote não encontrado")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<LotResponse> update(
            @Parameter(description = "ID do lote", required = true)
            @PathVariable Long id,
            @Valid @RequestBody LotRequest request,
            @Parameter(hidden = true) @CurrentUser User user) {
        LotResponse response = updateLotUseCase.execute(id, request, user);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Lista todos os lotes",
            description = "Retorna uma lista paginada de lotes ativos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de lotes retornada com sucesso")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping
    public ResponseEntity<Page<LotResponse>> listAll(@ParameterObject Pageable pageable) {

        Page<Lot> lots = findPagedLotsUseCase.execute(pageable);

        Page<LotResponse> response = lots.map(lot -> {
            var invoices = lotInvoiceService.findAllByLotId(lot.getId());
            return lotMapper.toResponse(lot, invoices);
        });

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Busca lote por ID",
            description = "Retorna um lote específico pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lote encontrado"),
                    @ApiResponse(responseCode = "404", description = "Lote não encontrado")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping("/{id}")
    public ResponseEntity<LotResponse> getById(
            @Parameter(description = "ID do lote", required = true)
            @PathVariable Long id) {

        Lot lot = findLotByIdUseCase.execute(id);

        var lotInvoices = lotInvoiceService.findAllByLotId(lot.getId());

        LotResponse response = lotMapper.toResponse(lot, lotInvoices);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Remove um lote",
            description = "Remove logicamente um lote pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Lote removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Lote não encontrado")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do lote", required = true)
            @PathVariable Long id) {
        deleteLotUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
