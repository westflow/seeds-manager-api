package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "BagTypeCreateRequest", description = "Payload para criação de tipo de sacaria")
public class BagTypeRequest {

    @Schema(description = "Nome do tipo de sacaria", example = "Casa da Lavraria", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Nome do tipo de sacaria é obrigatório")
    private String name;
}
