package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CompanyUpdateRequest", description = "Payload para atualização de empresa (dados cadastrais e contato)")
public class CompanyUpdateRequest {

    @Schema(description = "Razão social", example = "WestFlow Sementes LTDA", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Razão social é obrigatória")
    @Size(max = 200, message = "Razão social deve ter no máximo 200 caracteres")
    private String legalName;

    @Schema(description = "Nome fantasia", example = "WestFlow")
    @Size(max = 200, message = "Nome fantasia deve ter no máximo 200 caracteres")
    private String tradeName;

    @Schema(description = "Email de contato da empresa", example = "contato@empresa.com")
    @Email(message = "Formato de email inválido")
    @Size(max = 150, message = "O email deve ter no máximo 150 caracteres")
    private String email;

    @Schema(description = "Telefone da empresa", example = "5511999999999")
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String phone;

    @Schema(description = "Endereço", example = "Rua X, 123")
    @Size(max = 200, message = "O endereço deve ter no máximo 200 caracteres")
    private String address;

    @Schema(description = "Cidade", example = "São Paulo")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    private String city;

    @Schema(description = "Estado (UF)", example = "SP")
    @Size(max = 2, message = "O estado deve ter 2 caracteres")
    private String state;

    @Schema(description = "CEP", example = "12345-678")
    @Size(max = 10, message = "O CEP deve ter no máximo 10 caracteres")
    private String zipCode;

    @Schema(description = "URL do logo da empresa", example = "https://cdn.empresa.com/logo.png")
    @Size(max = 500, message = "A URL do logo deve ter no máximo 500 caracteres")
    private String logoUrl;

    @Schema(description = "Cor primária da identidade visual", example = "#00A86B")
    @Size(max = 20, message = "A cor primária deve ter no máximo 20 caracteres")
    private String primaryColor;

    @Schema(description = "Cor secundária da identidade visual", example = "#004225")
    @Size(max = 20, message = "A cor secundária deve ter no máximo 20 caracteres")
    private String secondaryColor;
}
