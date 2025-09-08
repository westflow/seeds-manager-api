package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Schema(name = "LotWithdrawalRequest", description = "Payload para registrar saída de lote")
public class LotWithdrawalRequest {

    @Schema(
            description = "ID do lote que será retirado",
            example = "15",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "O lote é obrigatório")
    private Long lotId;

    @Schema(
            description = "Número da nota fiscal de saída",
            example = "NF123456",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "O número da nota fiscal de saída é obrigatório")
    private String invoiceNumber;

    @Schema(
            description = "Quantidade retirada em kg",
            example = "250.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "A quantidade de saída é obrigatória")
    @DecimalMin(value = "0.01", message = "A quantidade deve ser maior que zero")
    private BigDecimal quantity;

    @Schema(
            description = "Data da saída do lote",
            example = "2025-08-17",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "A data de saída é obrigatória")
    private LocalDate withdrawalDate;

    @Schema(
            description = "Estado (UF) destino",
            example = "SP",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "O estado é obrigatório (UF)")
    private String state;


    @Schema(
            description = "ID do cliente associado à saída",
            example = "7",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "O cliente é obrigatório")
    private Long clientId;
}
