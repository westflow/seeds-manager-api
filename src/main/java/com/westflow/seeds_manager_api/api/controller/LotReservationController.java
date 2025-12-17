package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.config.CurrentUser;
import com.westflow.seeds_manager_api.api.dto.request.LotReservationRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.application.usecase.lot.ReserveLotUseCase;
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

@Tag(name = "LotReservations", description = "Operações de reserva de lotes")
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class LotReservationController {

    private final ReserveLotUseCase reserveLotUseCase;

    @Operation(
            summary = "Cria uma nova reserva de lote",
            description = "Valida e registra uma nova reserva de lote vinculado ao usuário logado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reserva de lote criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD')")
    @PostMapping
    public ResponseEntity<LotReservationResponse> reserve(@Valid @RequestBody LotReservationRequest request,
                                                          @Parameter(hidden = true) @CurrentUser User user) {

        LotReservationResponse response = reserveLotUseCase.execute(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
