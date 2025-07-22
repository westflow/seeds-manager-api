package com.westflow.seeds_manager_api.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientCreateRequest {

    @NotBlank(message = "Número é obrigatório")
    private String number;

    @NotBlank(message = "Name é obrigatório")
    private String name;

    @Email(message = "Formato de email inválido")
    private String email;

    private String phone;
}
