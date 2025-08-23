package com.westflow.seeds_manager_api.api.dto.request;

import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String email;

    @Schema(
            description = "Senha do usuário",
            example = "senhaSegura123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Senha é obrigatória")
    private String password;

    @Schema(
            description = "Nome completo do usuário",
            example = "João da Silva",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Schema(
            description = "Cargo ou função do usuário",
            example = "Gerente de Produção"
    )
    private String position;

    @Schema(
            description = "Nível de acesso do usuário",
            example = "ADMIN",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Nível de acesso é obrigatório")
    private AccessLevel accessLevel;
}
