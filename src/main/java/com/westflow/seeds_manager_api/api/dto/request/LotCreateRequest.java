package com.westflow.seeds_manager_api.api.dto.request;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.LotType;
import com.westflow.seeds_manager_api.domain.enums.SeedType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LotCreateRequest {

    @NotBlank(message = "Número do lote é obrigatório")
    private String lotNumber;

    @NotNull(message = "Tipo de lote é obrigatório")
    private LotType lotType;

    @NotNull(message = "Semente é obrigatório")
    private Long seedId;

    @NotNull(message = "Tipo de semente é obrigatório")
    private SeedType seedType;

    @NotNull(message = "Categoria é obrigatória")
    private LotCategory category;

    @NotNull(message = "O peso da sacaria é obrigatório")
    @DecimalMin(value = "0.01", message = "O peso deve ser maior que zero")
    private BigDecimal bagWeight;

    @NotNull(message = "Saldo do lote é obrigatório")
    @DecimalMin(value = "0.00", message = "O saldo do lote não pode ser negativo")
    private BigDecimal balance;

    private String analysisBulletin;
    private LocalDate bulletinDate;

    @NotNull(message = "Nota fiscal é obrigatória")
    private Long invoiceId;

    @NotNull(message = "Sacaria é obrigatória")
    private String bagType;
    private LocalDate validityDate;
    private Integer seedScore;
}
