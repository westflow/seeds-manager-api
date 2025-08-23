package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(name = "LotReservationRequest", description = "Payload para reservar lote")
public class LotReservationRequest {

    @Schema(description = "ID do lote a ser reservado", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID do lote é obrigatório")
    private Long lotId;

    @Schema(description = "Quantidade reservada em kg", example = "500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A quantidade reservada é obrigatória")
    @DecimalMin(value = "0.01", message = "A quantidade deve ser maior que zero")
    private BigDecimal quantity;

    @Schema(description = "ID do cliente associado à reserva", example = "7")
    private Long clientId;
}
