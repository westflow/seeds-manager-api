package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "BagTypeRequest", description = "Payload para criação de tipo de sacaria")
public class BagTypeRequest {

    @Schema(description = "Nome do tipo de sacaria", example = "Casa da Lavraria", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Nome do tipo de sacaria é obrigatório")
    @Size(max = 100, message = "O nome do tipo de sacaria deve ter no máximo 100 caracteres")
    @Pattern(regexp = "^[\\p{L}0-9 .-]+", message = "O nome contém caracteres inválidos. Use apenas letras, números, espaços, pontos e hífens")
    private String name;
}
