package com.westflow.seeds_manager_api.api.dto.request;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.OperationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(name = "InvoiceCreateRequest", description = "Payload para criação de nota fiscal")
public class InvoiceCreateRequest {

    @Schema(description = "Número da nota fiscal", example = "NF-2025-001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Número da nota é obrigatório")
    private String invoiceNumber;

    @Schema(description = "Nome do produtor", example = "Fazenda Boa Terra", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Nome do produtor é obrigatório")
    private String producerName;

    @Schema(description = "ID da semente associada", example = "42", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Semente é obrigatória")
    private Long seedId;

    @Schema(description = "Peso total em quilogramas", example = "1500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Peso total em kg é obrigatório")
    @DecimalMin("0.01")
    private BigDecimal totalKg;

    @Schema(description = "Tipo de operação realizada", example = "TRANSFER", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Tipo de operação é obrigatório")
    private OperationType operationType;

    @Schema(description = "Número de autorização (se aplicável)", example = "AUT-9981")
    private String authNumber;

    @Schema(description = "Categoria do lote", example = "S1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Categoria é obrigatória")
    private LotCategory category;

    @Schema(description = "Pureza da semente", example = "98.5", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Pureza é obrigatória")
    @DecimalMin("0.00")
    private BigDecimal purity;

    @Schema(description = "Safra da produção", example = "2024/2025", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Safra é obrigatória")
    private String harvest;

    @Schema(description = "Estado (UF) de produção", example = "SP", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "UF de produção é obrigatória")
    private String productionState;

    @Schema(description = "Área plantada em hectares", example = "25.0")
    private BigDecimal plantedArea;

    @Schema(description = "Área aprovada em hectares", example = "23.5")
    private BigDecimal approvedArea;
}
