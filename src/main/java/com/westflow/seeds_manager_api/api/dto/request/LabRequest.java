package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "LabRequest", description = "Payload para criação de laboratório credenciado")
public class LabRequest {

    @Schema(description = "Nome do laboratório", example = "Plante Bem Lab. de Análise de Sementes Ltda", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Nome do laboratório é obrigatório")
    @Size(max = 150, message = "O nome do laboratório deve ter no máximo 150 caracteres")
    @Pattern(regexp = "^[\\p{L}0-9 .,'-]+", message = "Nome do laboratório contém caracteres inválidos")
    private String name;

    @Schema(description = "Estado do laboratório (UF)", example = "SP", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Estado é obrigatório")
    @Size(min = 2, max = 2, message = "UF deve ter exatamente 2 caracteres")
    @Pattern(regexp = "^[A-Z]{2}$", message = "UF deve ser a sigla do estado com 2 letras maiúsculas")
    private String state;

    @Schema(description = "Código RENASEM", example = "SP-16410/2017", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Código RENASEM é obrigatório")
    @Size(max = 30, message = "O código RENASEM deve ter no máximo 30 caracteres")
    @Pattern(regexp = "^[A-Z]{2}-\\d+/\\d{4}$", message = "Código RENASEM inválido. Use o formato UF-NNNN/AAAA")
    private String renasemCode;
}

