package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "SeedUpdateRequest", description = "Payload para atualização de semente")
public class SeedUpdateRequest {
    @NotBlank(message = "Espécie é obrigatória")
    private String species;

    @NotBlank(message = "Cultivar é obrigatória")
    private String cultivar;

    @NotNull(message = "Indicação se é protegida é obrigatória")
    private Boolean isProtected;
}

