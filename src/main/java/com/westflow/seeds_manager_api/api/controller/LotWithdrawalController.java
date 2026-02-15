package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.config.CurrentUser;
import com.westflow.seeds_manager_api.api.dto.request.LotWithdrawalRequest;
import com.westflow.seeds_manager_api.api.dto.request.LotWithdrawalUpdateRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
import com.westflow.seeds_manager_api.application.usecase.lot.DeleteLotWithdrawUseCase;
import com.westflow.seeds_manager_api.application.usecase.lot.UpdateLotWithdrawalUseCase;
import com.westflow.seeds_manager_api.application.usecase.lot.WithdrawLotUseCase;
import com.westflow.seeds_manager_api.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "LotWithdrawals", description = "Operações de baixa de lotes")
@RestController
@RequestMapping("/api/lot/withdrawals")
@RequiredArgsConstructor
public class LotWithdrawalController {

    private final WithdrawLotUseCase withdrawLotUseCase;
    private final DeleteLotWithdrawUseCase deleteLotWithdrawUseCase;
    private final UpdateLotWithdrawalUseCase updateLotWithdrawalUseCase;

    @Operation(
            summary = "Baixa de lotes",
            description = "Valida e baixa um novo lote vinculado ao usuário logado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Lote criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LotWithdrawalResponse> withdraw(@Valid @RequestBody LotWithdrawalRequest request,
                                                          @Parameter(hidden = true) @CurrentUser User user) {

        LotWithdrawalResponse response = withdrawLotUseCase.execute(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Atualiza uma baixa de lote",
            description = "Atualiza os dados de uma baixa de lote existente (quantidade, data e estado) e ajusta o saldo do lote",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Baixa de lote atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Baixa de lote não encontrada")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<LotWithdrawalResponse> update(
            @Parameter(description = "ID da baixa de lote", required = true)
            @PathVariable Long id,
            @Valid @RequestBody LotWithdrawalUpdateRequest request
    ) {
        LotWithdrawalResponse response = updateLotWithdrawalUseCase.execute(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Remove uma baixa de lote",
            description = "Remove logicamente uma baixa lote pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Baixa de lote removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Baixa de lote não encontrado")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da baixa de lote", required = true)
            @PathVariable Long id) {
        deleteLotWithdrawUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
