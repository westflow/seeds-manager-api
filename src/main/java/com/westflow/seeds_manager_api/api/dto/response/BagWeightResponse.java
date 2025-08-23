package com.westflow.seeds_manager_api.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(name = "BagWeightResponse", description = "Representação de peso de sacaria")
public class BagWeightResponse {

    @Schema(description = "ID do peso", example = "1")
    private Long id;

    @Schema(description = "Peso da sacaria", example = "25.00")
    private BigDecimal weight;
}
