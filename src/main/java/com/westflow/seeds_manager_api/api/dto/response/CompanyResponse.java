package com.westflow.seeds_manager_api.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Resposta de empresa")
public class CompanyResponse {

    @Schema(description = "ID da empresa", example = "1")
    private Long id;

    @Schema(description = "Razão social", example = "WestFlow Sementes LTDA")
    private String legalName;

    @Schema(description = "Nome fantasia", example = "WestFlow")
    private String tradeName;

    @Schema(description = "CNPJ", example = "12345678000190")
    private String cnpj;

    @Schema(description = "Código do tenant", example = "westflow")
    private String tenantCode;
}
