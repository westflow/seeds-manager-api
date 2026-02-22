package com.westflow.seeds_manager_api.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnicalResponsibleRequest {

    @NotNull
    private Long companyId;

    @NotBlank
    private String name;

    @NotBlank
    private String cpf;

    private String renasemNumber;

    private String creaNumber;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String phone;

    private String email;

    private Boolean isPrimary = false;
}

