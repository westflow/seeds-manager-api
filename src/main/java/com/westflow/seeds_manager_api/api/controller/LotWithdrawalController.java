package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.config.CurrentUser;
import com.westflow.seeds_manager_api.api.dto.request.LotWithdrawalRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
import com.westflow.seeds_manager_api.application.service.LotWithdrawalService;
import com.westflow.seeds_manager_api.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
public class LotWithdrawalController {

    private final LotWithdrawalService lotWithdrawalService;

    public LotWithdrawalController(LotWithdrawalService lotWithdrawalService) {
        this.lotWithdrawalService = lotWithdrawalService;
    }

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

        LotWithdrawalResponse response = lotWithdrawalService.withdraw(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
