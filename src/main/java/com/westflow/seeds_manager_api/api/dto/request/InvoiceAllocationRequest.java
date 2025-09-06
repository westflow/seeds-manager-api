package com.westflow.seeds_manager_api.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InvoiceAllocationRequest {
    @NotNull(message = "ID da nota fiscal é obrigatório")
    private Long invoiceId;

    @NotNull(message = "Quantidade a ser descontada é obrigatória")
    @DecimalMin(value = "0.01", message = "Quantidade deve ser maior que zero")
    private BigDecimal quantity;
}
