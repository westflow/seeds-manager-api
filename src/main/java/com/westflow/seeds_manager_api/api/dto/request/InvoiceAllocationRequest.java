package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(name = "InvoiceAllocationRequest", description = "Payload para alocação de nota fiscal em lote")
public class InvoiceAllocationRequest {
    @Schema(description = "ID da nota fiscal", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID da nota fiscal é obrigatório")
    private Long invoiceId;

    @Schema(description = "Quantidade a ser alocada (em kg)", example = "1000.50", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Quantidade a ser alocada é obrigatória")
    @DecimalMin(value = "0.01", message = "Quantidade deve ser maior que zero")
    @DecimalMax(value = "9999999.99", message = "Quantidade máxima permitida é 9.999.999,99")
    @Digits(integer = 7, fraction = 2, message = "A quantidade deve ter no máximo 7 dígitos inteiros e 2 casas decimais")
    private BigDecimal quantity;
}
