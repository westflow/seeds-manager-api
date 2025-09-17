package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "LabRequest", description = "Payload para criação de laboratório credenciado")
public class LabRequest {

    @Schema(description = "Nome do laboratório", example = "Plante Bem Lab. de Análise de Sementes Ltda", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Nome do laboratório é obrigatório")
    private String name;

    @Schema(description = "Estado do laboratório", example = "SP", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Estado é obrigatório")
    private String state;

    @Schema(description = "Código RENASEM", example = "SP-16410/2017", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Código RENASEM é obrigatório")
    private String renasemCode;
}

