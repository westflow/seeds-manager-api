package com.westflow.seeds_manager_api.api.dto.request;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.LotType;
import com.westflow.seeds_manager_api.domain.enums.SeedType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
            description = "Tamanho da sacaria",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "O tamanho da sacaria é obrigatório")
    private Long bagWeightId;

    @Schema(
            description = "Tipo da sacaria",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Tipo da sacaria é obrigatória")
    private Long bagTypeId;

    @Schema(
            description = "Quantidade total do lote em kg",
            example = "1000.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Quantidade total é obrigatória")
    @DecimalMin(value = "0.01", message = "A quantidade total do lote deve ser maior que zero")
    @Digits(integer = 10, fraction = 2, message = "A quantidade deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
    private BigDecimal quantityTotal;

    @Schema(description = "Ordem de produção do lote", example = "OP-2025-001")
    @Size(max = 50, message = "A ordem de produção deve ter no máximo 50 caracteres")
    @Pattern(regexp = "^[A-Z0-9-]*$", message = "A ordem de produção deve conter apenas letras maiúsculas, números e hífens")
    private String productionOrder;

    @Schema(description = "Número do boletim de análise", example = "BA-2025-001")
    @Size(max = 50, message = "O número do boletim de análise deve ter no máximo 50 caracteres")
    @Pattern(regexp = "^[A-Z0-9-]*$", message = "O número do boletim deve conter apenas letras maiúsculas, números e hífens")
    private String analysisBulletin;

    @Schema(description = "Data do boletim de análise (formato: yyyy-MM-dd)", example = "2025-07-27")
    private LocalDate bulletinDate;

    @Schema(description = "Quantidade de sementes duras", example = "0")
    @Min(value = 0, message = "A quantidade de sementes duras não pode ser negativa")
    @Max(value = 100, message = "A quantidade de sementes duras não pode ser maior que 100")
    private Integer hardSeeds = 0;

    @Schema(description = "Quantidade de sementes silvestres", example = "0")
    @Min(value = 0, message = "A quantidade de sementes silvestres não pode ser negativa")
    @Max(value = 100, message = "A quantidade de sementes silvestres não pode ser maior que 100")
    private Integer wildSeeds = 0;

    @Schema(description = "Quantidade de outras espécies cultivadas", example = "0")
    @Min(value = 0, message = "A quantidade de outras espécies cultivadas não pode ser negativa")
    @Max(value = 100, message = "A quantidade de outras espécies cultivadas não pode ser maior que 100")
    private Integer otherCultivatedSpecies = 0;

    @Schema(description = "Quantidade de sementes toleradas", example = "0")
    @Min(value = 0, message = "A quantidade de sementes toleradas não pode ser negativa")
    @Max(value = 100, message = "A quantidade de sementes toleradas não pode ser maior que 100")
    private Integer tolerated = 0;

    @Schema(description = "Quantidade de sementes proibidas", example = "0")
    @Min(value = 0, message = "A quantidade de sementes proibidas não pode ser negativa")
    @Max(value = 100, message = "A quantidade de sementes proibidas não pode ser maior que 100")
    private Integer prohibited = 0;

    @Schema(description = "ID do laboratório responsável pela análise", example = "1")
    private Long labId;

    @Schema(description = "Notas fiscais e respectivas quantidades a serem descontadas")
    @NotNull(message = "Notas fiscais são obrigatórias")
    @Size(min = 1, message = "O lote deve conter pelo menos uma nota fiscal")
    private List<InvoiceAllocationRequest> invoiceAllocations;

    @Schema(description = "Data de validade do lote (formato: yyyy-MM-dd)", example = "2026-07-27")
    @Future(message = "A data de validade deve ser uma data futura")
    private LocalDate validityDate;

    @Schema(description = "Pontuação da semente (0-100)", example = "85")
    @Min(value = 0, message = "A pontuação da semente não pode ser menor que 0")
    @Max(value = 100, message = "A pontuação da semente não pode ser maior que 100")
    private Integer seedScore = 0;

    @Schema(description = "Pureza da semente (0.00 a 100.00)", example = "90.50")
    @DecimalMin(value = "0.00", message = "A pureza não pode ser menor que 0.00")
    @DecimalMax(value = "100.00", message = "A pureza não pode ser maior que 100.00")
    @Digits(integer = 3, fraction = 2, message = "A pureza deve ter no máximo 3 dígitos inteiros e 2 casas decimais")
    private BigDecimal purity = new BigDecimal("0.00");
}
