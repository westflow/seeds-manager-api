package com.westflow.seeds_manager_api.api.dto.request;

import com.westflow.seeds_manager_api.domain.enums.LotStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LotReservationRequest {

    @NotNull(message = "O ID do lote é obrigatório")
    private Long lotId;

    @NotNull(message = "A quantidade reservada é obrigatória")
    @DecimalMin(value = "0.01", message = "A quantidade deve ser maior que zero")
    private BigDecimal quantity;

    @NotNull(message = "A data da reserva é obrigatória")
    private LocalDate reservationDate;

    @NotNull(message = "O status da reserva é obrigatório")
    private LotStatus status; // Enum: PENDING, CONFIRMED, CANCELLED

    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clientId;
}
