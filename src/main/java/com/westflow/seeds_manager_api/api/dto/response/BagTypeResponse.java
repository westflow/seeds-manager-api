package com.westflow.seeds_manager_api.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "BagTypeResponse", description = "Representação de tipo de sacaria")
public class BagTypeResponse {

    @Schema(description = "ID do tipo de sacaria", example = "1")
    private Long id;

    @Schema(description = "Nome do tipo de sacaria", example = "Big Bag 1000L")
    private String name;
}
