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

    @Schema(description = "URL do logo da empresa", example = "https://cdn.empresa.com/logo.png")
    private String logoUrl;

    @Schema(description = "Cor primária da identidade visual", example = "#00A86B")
    private String primaryColor;

    @Schema(description = "Cor secundária da identidade visual", example = "#004225")
    private String secondaryColor;

    @Schema(description = "Email de contato da empresa", example = "contato@empresa.com")
    private String email;

    @Schema(description = "Telefone da empresa", example = "5511999999999")
    private String phone;

    @Schema(description = "Endereço", example = "Rua X, 123")
    private String address;

    @Schema(description = "Cidade", example = "São Paulo")
    private String city;

    @Schema(description = "Estado (UF)", example = "SP")
    private String state;

    @Schema(description = "CEP", example = "12345-678")
    private String zipCode;
}
