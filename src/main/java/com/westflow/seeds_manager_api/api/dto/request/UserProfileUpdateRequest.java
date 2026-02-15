package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payload para atualização de perfil do usuário logado")
public class UserProfileUpdateRequest {

    @Schema(description = "Nome do usuário", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Nome é obrigatório")
    @Pattern(regexp = "^[\\p{L}\\s.'-]+", message = "Nome contém caracteres inválidos")
    private String name;

    @Schema(description = "Cargo do usuário", example = "Gerente de Produção")
    @Pattern(regexp = "^[\\p{L}\\s0-9.,'-]*$", message = "Cargo contém caracteres inválidos")
    private String position;
}
