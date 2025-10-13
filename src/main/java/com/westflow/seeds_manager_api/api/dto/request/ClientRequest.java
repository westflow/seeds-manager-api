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
@Schema(name = "ClientRequest", description = "Payload para criação de cliente")
public class ClientRequest {

    @Schema(
            description = "Número identificador do cliente",
            example = "123456",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Número é obrigatório")
    @Size(min = 3, max = 20, message = "O número deve ter entre 3 e 20 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9\\-]+$", message = "O número deve conter apenas letras, números e hífens")
    private String number;

    @Schema(
            description = "Nome do cliente",
            example = "João Silva",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Pattern(regexp = "^[\\p{L} .'-]+", message = "Nome contém caracteres inválidos")
    private String name;

    @Schema(description = "Email do cliente", example = "joao@email.com")
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    private String email;

    @Schema(description = "Telefone do cliente", example = "(11) 91234-5678")
    @Pattern(regexp = "^(\\(\\d{2}\\)\\s?\\d{4,5}-?\\d{4}|\\d{10,11})?$", 
             message = "Formato de telefone inválido. Use (DD) 99999-9999 ou 11999999999")
    private String phone;
}
