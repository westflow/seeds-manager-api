package com.westflow.seeds_manager_api.api.dto.request;

import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payload para atualização de usuário por um administrador")
public class UserAdminUpdateRequest {

    @Schema(description = "Novo email do usuário", example = "usuario@westflow.com")
    @Email(message = "Formato de email inválido")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$",
             message = "Formato de email inválido")
    private String email;

    @Schema(description = "Nova senha do usuário", example = "NovaSenha123")
    private String password;

    @Schema(description = "Nome do usuário", example = "João da Silva")
    @Pattern(regexp = "^[\\p{L}\\s.'-]+", message = "Nome contém caracteres inválidos")
    private String name;

    @Schema(description = "Cargo do usuário", example = "Gerente de Produção")
    @Pattern(regexp = "^[\\p{L}\\s0-9.,'-]*$", message = "Cargo contém caracteres inválidos")
    private String position;

    @Schema(description = "Nível de acesso do usuário", example = "STANDARD")
    private AccessLevel accessLevel;
}
