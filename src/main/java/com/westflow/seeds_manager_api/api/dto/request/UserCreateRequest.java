package com.westflow.seeds_manager_api.api.dto.request;

import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "UserCreateRequest", description = "Payload para criação de usuário")
public class UserCreateRequest {

    @Schema(
            description = "Email do usuário",
            example = "usuario@westflow.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    private String email;

    @Schema(
            description = "Senha do usuário",
            example = "senhaSegura123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, max = 64, message = "A senha deve ter entre 8 e 64 caracteres")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
        message = "A senha deve conter pelo menos 1 letra maiúscula, 1 minúscula, 1 número e 1 caractere especial"
    )
    private String password;

    @Schema(
            description = "Nome completo do usuário",
            example = "João da Silva",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Pattern(regexp = "^[\\p{L} .'-]+", message = "Nome contém caracteres inválidos")
    private String name;

    @Schema(
            description = "Cargo ou função do usuário",
            example = "Gerente de Produção"
    )
    @Size(max = 100, message = "O cargo deve ter no máximo 100 caracteres")
    private String position;

    @Schema(
            description = "Nível de acesso do usuário",
            example = "ADMIN",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Nível de acesso é obrigatório")
    private AccessLevel accessLevel;
}
