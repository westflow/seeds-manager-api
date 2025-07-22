package com.westflow.seeds_manager_api.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LotWithdrawalRequest {

    @NotNull(message = "O ID do lote é obrigatório")
    private Long lotId;

    private String invoiceNumber;

    @NotNull(message = "A quantidade de saída é obrigatória")
    @DecimalMin(value = "0.01", message = "A quantidade deve ser maior que zero")
    private BigDecimal quantity;

    @NotNull(message = "A data de saída é obrigatória")
    private LocalDate withdrawalDate;

    @NotBlank(message = "O estado é obrigatório (UF)")
    private String state;

    @NotNull(message = "O ID do usuário responsável é obrigatório")
    private Long userId;

    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clientId;
}
