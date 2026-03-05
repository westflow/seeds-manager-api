package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "TechnicalResponsibleUpdateRequest", description = "Payload para atualização de responsável técnico")
public class TechnicalResponsibleUpdateRequest {

    @Schema(description = "Nome do responsável técnico", example = "Homero de Assumpção Fernandes Silva")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String name;

    @Schema(description = "CPF do responsável (11 dígitos ou formato xxx.xxx.xxx-xx)", example = "062.054.358-29")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$|^\\d{11}$", message = "CPF inválido. Use 11 dígitos ou formato xxx.xxx.xxx-xx")
    private String cpf;

    @Schema(description = "Renasem number", example = "00141/2006")
    @Size(max = 50, message = "Renasem deve ter no máximo 50 caracteres")
    private String renasemNumber;

    @Schema(description = "CREA number", example = "130.544/D")
    @Size(max = 50, message = "CREA deve ter no máximo 50 caracteres")
    private String creaNumber;

    @Schema(description = "Endereço completo", example = "Al Ana Maia Eugênio, 160")
    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    private String address;

    @Schema(description = "Município", example = "Presidente Prudente")
    @Size(max = 120, message = "Cidade deve ter no máximo 120 caracteres")
    private String city;

    @Schema(description = "UF (estado, 2 letras)", example = "SP")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Estado deve conter duas letras maiúsculas, ex: SP")
    private String state;

    @Schema(description = "CEP (formato 00000-000)", example = "19053-900")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP inválido. Use 99999-999 ou 99999999")
    private String zipCode;

    @Schema(description = "Telefone", example = "(18) 3908-4758")
    @Pattern(regexp = "^[0-9()+\\- ]{6,30}$", message = "Telefone inválido")
    private String phone;

    @Schema(description = "E-mail", example = "tecnico@westflow.com.br")
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @Schema(description = "Indica se é o responsável técnico principal (usado ao gerar termo de conformidade)", example = "false")
    private Boolean isPrimary;
}

