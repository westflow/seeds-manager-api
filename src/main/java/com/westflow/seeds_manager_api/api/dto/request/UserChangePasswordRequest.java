package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payload para alteração de senha do usuário logado")
public class UserChangePasswordRequest {

    @Schema(description = "Senha atual", example = "SenhaAntiga123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Senha atual é obrigatória")
    private String currentPassword;

    @Schema(description = "Nova senha", example = "NovaSenha123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Nova senha é obrigatória")
    private String newPassword;
}
