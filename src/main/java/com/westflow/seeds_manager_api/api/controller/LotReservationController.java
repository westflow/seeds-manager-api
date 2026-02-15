package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.config.CurrentUser;
import com.westflow.seeds_manager_api.api.dto.request.LotReservationRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.application.usecase.lot.ReserveLotUseCase;
import com.westflow.seeds_manager_api.application.usecase.lot.CancelLotReservationUseCase;
import com.westflow.seeds_manager_api.application.usecase.lot.FindLotReservationsByLotIdUseCase;
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

@Tag(name = "LotReservations", description = "Operações de reserva de lotes")
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class LotReservationController {

    private final ReserveLotUseCase reserveLotUseCase;
    private final CancelLotReservationUseCase cancelLotReservationUseCase;
    private final FindLotReservationsByLotIdUseCase findLotReservationsByLotIdUseCase;

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

    @Operation(
            summary = "Lista reservas de um lote",
            description = "Retorna uma lista paginada de reservas associadas a um lote específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD')")
    @GetMapping("/lot/{lotId}")
    public ResponseEntity<Page<LotReservationResponse>> listByLot(
            @PathVariable Long lotId,
            @ParameterObject Pageable pageable
    ) {
        Page<LotReservationResponse> page = findLotReservationsByLotIdUseCase.execute(lotId, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Cancela uma reserva de lote",
            description = "Cancela logicamente uma reserva de lote e atualiza o saldo do lote",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Reserva cancelada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Reserva não pode ser cancelada"),
                    @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD')")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        cancelLotReservationUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
