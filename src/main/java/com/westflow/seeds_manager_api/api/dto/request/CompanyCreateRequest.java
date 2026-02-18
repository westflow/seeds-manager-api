package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CompanyCreateRequest", description = "Payload para criação de empresa e usuário OWNER")
public class CompanyCreateRequest {

    @Schema(description = "Código do tenant (usado em URLs)", example = "westflow", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "tenant_code é obrigatório")
    @Size(min = 3, max = 50, message = "tenant_code deve ter entre 3 e 50 caracteres")
    @Pattern(regexp = "^[a-z0-9]+$", message = "tenant_code deve conter apenas letras minúsculas e números, sem espaços")
    private String tenantCode;

    @Schema(description = "Razão social", example = "WestFlow Sementes LTDA", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Razão social é obrigatória")
    @Size(max = 200, message = "Razão social deve ter no máximo 200 caracteres")
    private String legalName;

    @Schema(description = "Nome fantasia", example = "WestFlow")
    @Size(max = 200, message = "Nome fantasia deve ter no máximo 200 caracteres")
    private String tradeName;

    @Schema(description = "CNPJ (com ou sem máscara)", example = "12345678000190", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "CNPJ é obrigatório")
    @Size(min = 14, max = 18, message = "CNPJ deve ter entre 14 e 18 caracteres")
    private String cnpj;

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

    @Schema(description = "Dados do usuário OWNER a ser criado", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Dados do OWNER são obrigatórios")
    @Valid
    private CompanyOwnerRequest owner;
}
