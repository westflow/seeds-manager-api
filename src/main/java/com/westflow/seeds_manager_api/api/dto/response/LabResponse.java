package com.westflow.seeds_manager_api.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "LabResponse", description = "Representação de laboratório credenciado")
public class LabResponse {

    @Schema(description = "ID do laboratório", example = "1")
    private Long id;

    @Schema(description = "Nome do laboratório", example = "Plante Bem Lab. de Análise de Sementes Ltda")
    private String name;

    @Schema(description = "Estado do laboratório", example = "SP")
    private String state;

    @Schema(description = "Código RENASEM", example = "SP-16410/2017")
    private String renasemCode;
}
