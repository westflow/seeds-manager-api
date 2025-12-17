package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.config.CurrentUser;
import com.westflow.seeds_manager_api.api.dto.request.LotWithdrawalRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "LotWithdrawals", description = "Operações de baixa de lotes")
@RestController
@RequestMapping("/api/lot/withdrawals")
@RequiredArgsConstructor
public class LotWithdrawalController {

    private final WithdrawLotUseCase withdrawLotUseCase;

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
}
