package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(name = "BagWeightRequest", description = "Payload para criação de peso de sacaria")
public class BagWeightRequest {

    @Schema(description = "Peso da sacaria em gramas", example = "25000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Peso é obrigatório")
    @DecimalMin(value = "0.10", message = "Peso mínimo permitido é 0.10g")
    @DecimalMax(value = "1000000.00", message = "Peso máximo permitido é 1.000.000g (1 tonelada)")
    @Digits(integer = 7, fraction = 2, message = "O peso deve ter no máximo 7 dígitos inteiros e 2 casas decimais")
    private BigDecimal weight;
}

