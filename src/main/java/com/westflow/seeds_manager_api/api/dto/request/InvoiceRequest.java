package com.westflow.seeds_manager_api.api.dto.request;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.OperationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(name = "InvoiceCreateRequest", description = "Payload para criação de nota fiscal")
public class InvoiceRequest {

    @Schema(description = "Número da nota fiscal", example = "NF-2025-001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Número da nota é obrigatório")
    @Size(max = 50, message = "O número da nota fiscal deve ter no máximo 50 caracteres")
    @Pattern(regexp = "^[A-Z0-9\\-]+$", message = "Número da nota fiscal contém caracteres inválidos")
    private String invoiceNumber;

    @Schema(description = "Nome do produtor", example = "Fazenda Boa Terra", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Nome do produtor é obrigatório")
    @Size(max = 100, message = "O nome do produtor deve ter no máximo 100 caracteres")
    @Pattern(regexp = "^[\\p{L}0-9 .,'-]+", message = "Nome do produtor contém caracteres inválidos")
    private String producerName;

    @Schema(description = "ID da semente associada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Semente é obrigatória")
    private Long seedId;

    @Schema(description = "Peso total em KG", example = "1500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Peso total é obrigatório")
    @DecimalMin(value = "0.01", message = "Peso total deve ser maior que zero")
    @DecimalMax(value = "9999999.99", message = "Peso máximo permitido é 9.999.999,99 kg")
    @Digits(integer = 7, fraction = 2, message = "O peso deve ter no máximo 7 dígitos inteiros e 2 casas decimais")
    private BigDecimal totalKg;

    @Schema(description = "Tipo de operação", example = "TRANSFER", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Tipo de operação é obrigatório")
    private OperationType operationType;

    @Schema(description = "Número de autorização", example = "AUT-9981")
    @Size(max = 50, message = "O número de autorização deve ter no máximo 50 caracteres")
    @Pattern(regexp = "^[A-Z0-9\\-]*$", message = "Número de autorização contém caracteres inválidos")
    private String authNumber;

    @Schema(description = "Categoria do lote", example = "S1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Categoria é obrigatória")
    private LotCategory category;

    @Schema(description = "Pureza da semente (0-100%)", example = "98.50", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Pureza é obrigatória")
    @DecimalMin(value = "0.00", message = "Pureza não pode ser negativa")
    @DecimalMax(value = "100.00", message = "Pureza máxima é 100%")
    @Digits(integer = 3, fraction = 2, message = "A pureza deve ter no máximo 3 dígitos inteiros e 2 casas decimais")
    private BigDecimal purity;

    @Schema(description = "Safra da produção", example = "2024/2025", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Safra é obrigatória")
    @Size(max = 20, message = "A safra deve ter no máximo 20 caracteres")
    @Pattern(regexp = "^[0-9]{4}[/-][0-9]{2,4}$", message = "Formato de safra inválido. Use o formato AAAA/AA ou AAAA/AAAA")
    private String harvest;

    @Schema(description = "Estado (UF) de produção", example = "SP", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "UF de produção é obrigatória")
    @Size(min = 2, max = 2, message = "UF deve ter exatamente 2 caracteres")
    @Pattern(regexp = "^[A-Z]{2}$", message = "UF deve ser a sigla do estado com 2 letras maiúsculas")
    private String productionState;

    @Schema(description = "Área plantada em hectares", example = "100.50")
    @DecimalMin(value = "0.01", message = "Área plantada deve ser maior que zero")
    @DecimalMax(value = "9999999.99", message = "Área plantada máxima é 9.999.999,99 ha")
    @Digits(integer = 7, fraction = 2, message = "A área plantada deve ter no máximo 7 dígitos inteiros e 2 casas decimais")
    private BigDecimal plantedArea;

    @Schema(description = "Área aprovada em hectares", example = "95.50")
    @DecimalMin(value = "0.01", message = "Área aprovada deve ser maior que zero")
    @DecimalMax(value = "9999999.99", message = "Área aprovada máxima é 9.999.999,99 ha")
    @Digits(integer = 7, fraction = 2, message = "A área aprovada deve ter no máximo 7 dígitos inteiros e 2 casas decimais")
    private BigDecimal approvedArea;
}
