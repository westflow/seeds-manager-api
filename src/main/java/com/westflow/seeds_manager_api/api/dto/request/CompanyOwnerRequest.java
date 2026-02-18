package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CompanyOwnerRequest", description = "Dados do usuário OWNER criado junto com a empresa")
public class CompanyOwnerRequest {

    @Schema(description = "Email do OWNER", example = "owner@empresa.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email do OWNER é obrigatório")
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$",
             message = "Formato de email inválido")
    private String email;

    @Schema(description = "Senha do OWNER", example = "SenhaForte123!*", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Senha do OWNER é obrigatória")
    @Size(min = 8, max = 64, message = "A senha deve ter entre 8 e 64 caracteres")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9\\s]).{8,}$",
            message = "A senha deve conter pelo menos 1 letra maiúscula, 1 minúscula, 1 número e 1 caractere especial"
    )
    private String password;

    @Schema(description = "Nome completo do OWNER", example = "Dono da Empresa", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Nome do OWNER é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Pattern(regexp = "^[\\p{L}\\s.'-]+", message = "Nome contém caracteres inválidos")
    private String name;

    @Schema(description = "Cargo do OWNER", example = "Proprietário")
    @Size(max = 100, message = "O cargo deve ter no máximo 100 caracteres")
    @Pattern(regexp = "^[\\p{L}\\s0-9.,'-]*$", message = "Cargo contém caracteres inválidos")
    private String position;
}
