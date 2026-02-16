package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payload para reset de senha via token")
public class ResetPasswordRequest {

    @Schema(description = "Token de reset recebido por email", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Token de reset é obrigatório")
    private String token;

    @Schema(description = "Nova senha", example = "NovaSenhaSegura123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Nova senha é obrigatória")
    private String newPassword;
}
