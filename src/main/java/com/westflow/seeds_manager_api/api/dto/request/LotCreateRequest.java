package com.westflow.seeds_manager_api.api.dto.request;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.LotType;
import com.westflow.seeds_manager_api.domain.enums.SeedType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Quantidade total é obrigatório")
    @DecimalMin(value = "0.01", message = "A quantidade total do lote deve ser maior que zero")
    private BigDecimal quantityTotal;

    @Schema(description = "Ordem de produção do lote", example = "OP-2025-001")
    private String productionOrder;

    @Schema(description = "Número do boletim de análise", example = "BA-2025-001")
    private String analysisBulletin;

    @Schema(description = "Data do boletim de análise", example = "2025-07-27")
    private LocalDate bulletinDate;

    @Schema(description = "Quantidade de sementes duras", example = "0")
    private Integer hardSeeds = 0;

    @Schema(description = "Quantidade de sementes silvestres", example = "0")
    private Integer wildSeeds = 0;

    @Schema(description = "Quantidade de outras espécies cultivadas", example = "0")
    private Integer otherCultivatedSpecies = 0;

    @Schema(description = "Quantidade de sementes toleradas", example = "0")
    private Integer tolerated = 0;

    @Schema(description = "Quantidade de sementes proibidas", example = "0")
    private Integer prohibited = 0;

    @Schema(description = "Laboratório", example = "1")
    private Long LabId;

    @Schema(description = "Notas fiscais e respectivas quantidades a serem descontadas")
    @NotNull(message = "Notas fiscais são obrigatórias")
    @Size(min = 1, message = "O lote deve conter pelo menos uma nota fiscal")
    private List<InvoiceAllocationRequest> invoiceAllocations;

    @Schema(description = "Data de validade do lote", example = "2026-07-27")
    private LocalDate validityDate;

    @Schema(description = "Pontuação da semente", example = "85")
    private Integer seedScore;

    @Schema(description = "Pureza da semente", example = "90.5")
    private BigDecimal purity;
}
