package com.westflow.seeds_manager_api.api.dto.request;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.OperationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InvoiceCreateRequest {

    @NotBlank(message = "Número da nota é obrigatório")
    private String invoiceNumber;

    @NotBlank(message = "Nome do produtor é obrigatório")
    private String producerName;

    @NotNull(message = "Semente é obrigatória")
    private Long seedId;

    @NotNull(message = "Peso total em kg é obrigatório")
    @DecimalMin("0.01")
    private BigDecimal totalKg;

    @NotBlank(message = "Tipo de operação é obrigatório")
    private OperationType operationType;

    private String authNumber;

    @NotBlank(message = "Categoria é obrigatória")
    private LotCategory category;

    @NotNull(message = "Pureza é obrigatória")
    @DecimalMin("0.00")
    private BigDecimal purity;

    @NotBlank(message = "Safra é obrigatória")
    private String harvest;

    @NotBlank(message = "UF de produção é obrigatória")
    private String productionState;

    @NotNull(message = "Área plantada é obrigatória")
    private BigDecimal plantedArea;

    @NotNull(message = "Área aprovada é obrigatória")
    private BigDecimal approvedArea;
}
