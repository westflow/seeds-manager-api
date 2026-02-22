package com.westflow.seeds_manager_api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TechnicalResponsibleResponse {
    private Long id;
    private Long companyId;
    private String name;
    private String cpf;
    private String renasemNumber;
    private String creaNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phone;
    private String email;
    private Boolean isPrimary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;
}

