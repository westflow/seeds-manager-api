package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "BagTypeCreateRequest", description = "Payload para criação de tipo de sacaria")
public class BagTypeCreateRequest {

    @Schema(description = "Nome do tipo de sacaria", example = "Big Bag 1000L", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Nome do tipo de sacaria é obrigatório")
    private String name;
}
