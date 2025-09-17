package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    private String number;

    @Schema(
            description = "Nome do cliente",
            example = "João Silva",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Name é obrigatório")
    private String name;

    @Schema(description = "Email do cliente", example = "joao@email.com")
    @Email(message = "Formato de email inválido")
    private String email;

    @Schema(description = "Telefone do cliente", example = "(11) 91234-5678")
    private String phone;
}
