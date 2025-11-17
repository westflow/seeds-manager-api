package com.westflow.seeds_manager_api.api.dto.response;

import com.westflow.seeds_manager_api.domain.enums.AccessLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Resposta de usuário")
public class UserResponse {

    @Schema(description = "ID do usuário", example = "1")
    private Long id;

    @Schema(description = "Email do usuário", example = "usuario@westflow.com")
    @Email(message = "Formato de email inválido")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$", 
             message = "Formato de email inválido")
    private String email;

    @Schema(description = "Nome do usuário", example = "João da Silva")
    @Pattern(regexp = "^[\\p{L}\\s.'-]+", message = "Nome contém caracteres inválidos")
    private String name;

    @Schema(description = "Cargo do usuário", example = "Gerente de Produção", nullable = true)
    @Pattern(regexp = "^[\\p{L}\\s0-9.,'-]*$", message = "Cargo contém caracteres inválidos")
    private String position;

    @Schema(description = "Nível de acesso do usuário", example = "ADMIN")
    private AccessLevel accessLevel;
}
