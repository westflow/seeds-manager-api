package com.westflow.seeds_manager_api.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SeedCreateRequest {

    @NotBlank(message = "Espécie é obrigatória")
    private String species;

    @NotBlank(message = "Cultivar é obrigatória")
    private String cultivar;

    private boolean isProtected;
}
