package com.westflow.seeds_manager_api.api.dto.request;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.LotType;
import com.westflow.seeds_manager_api.domain.enums.SeedType;
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
@Schema(name = "LotCreateRequest", description = "Payload para criação de lote")
public class LotCreateRequest {

    @Schema(
            description = "Tipo de lote",
            example = "INTERNAL_SALE",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Tipo de lote é obrigatório")
    private LotType lotType;

    @Schema(
            description = "ID da semente associada",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Semente é obrigatório")
    private Long seedId;

    @Schema(
            description = "Tipo de semente",
            example = "CONVENTIONAL",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Tipo de semente é obrigatório")
    private SeedType seedType;

    @Schema(
            description = "Categoria do lote",
            example = "S1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Categoria é obrigatória")
    private LotCategory category;

    @Schema(
            description = "Peso da sacaria em kg",
            example = "10.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "O peso da sacaria é obrigatório")
    @DecimalMin(value = "0.01", message = "O peso deve ser maior que zero")
    private BigDecimal bagWeight;

    @Schema(
            description = "Saldo disponível do lote em kg",
            example = "1000.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Saldo do lote é obrigatório")
    @DecimalMin(value = "0.00", message = "O saldo do lote não pode ser negativo")
    private BigDecimal balance;

    @Schema(description = "Número do boletim de análise", example = "BA-2025-001")
    private String analysisBulletin;

    @Schema(description = "Data do boletim de análise", example = "2025-07-27")
    private LocalDate bulletinDate;

    @Schema(
            description = "ID da nota fiscal associada",
            example = "12",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Nota fiscal é obrigatória")
    private Long invoiceId;

    @Schema(
            description = "Tipo de sacaria utilizada",
            example = "Casa da Lavoura",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Sacaria é obrigatória")
    private String bagType;

    @Schema(description = "Data de validade do lote", example = "2026-07-27")
    private LocalDate validityDate;

    @Schema(description = "Pontuação da semente", example = "85")
    private Integer seedScore;
}
