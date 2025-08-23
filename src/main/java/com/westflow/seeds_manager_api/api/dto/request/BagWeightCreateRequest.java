package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(name = "BagWeightCreateRequest", description = "Payload para criação de peso de sacaria")
public class BagWeightCreateRequest {

    @Schema(description = "Peso da sacaria", example = "25.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Peso é obrigatório")
    @DecimalMin(value = "0.01", message = "Peso deve ser maior que zero")
    private BigDecimal weight;
}

