package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(name = "SeedRequest", description = "Payload para criação de semente")
public class SeedRequest {

    @Schema(
            description = "Espécie da semente",
            example = "Urochloa Brizantha",
            requiredMode = RequiredMode.REQUIRED
    )
    @NotBlank(message = "Espécie é obrigatória")
    private String species;

    @Schema(
            description = "Cultivar da semente",
            example = "Marandu",
            requiredMode = RequiredMode.REQUIRED
    )
    @NotBlank(message = "Cultivar é obrigatória")
    private String cultivar;

    @Schema(
            description = "Indica se a semente é protegida",
            example = "false",
            requiredMode = RequiredMode.REQUIRED
    )
    private boolean isProtected;
}
