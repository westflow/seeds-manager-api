package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(max = 100, message = "A espécie deve ter no máximo 100 caracteres")
    @Pattern(regexp = "^[\\p{L}0-9 .-]+", message = "A espécie contém caracteres inválidos. Use apenas letras, números, espaços, pontos e hífens")
    private String species;

    @Schema(
            description = "Cultivar da semente",
            example = "Marandu",
            requiredMode = RequiredMode.REQUIRED
    )
    @NotBlank(message = "Cultivar é obrigatória")
    @Size(max = 100, message = "O cultivar deve ter no máximo 100 caracteres")
    @Pattern(regexp = "^[\\p{L}0-9 .-]+", message = "O cultivar contém caracteres inválidos. Use apenas letras, números, espaços, pontos e hífens")
    private String cultivar;

    @Schema(
            description = "Indica se a semente é protegida",
            example = "false",
            requiredMode = RequiredMode.REQUIRED
    )
    private boolean isProtected;
}
