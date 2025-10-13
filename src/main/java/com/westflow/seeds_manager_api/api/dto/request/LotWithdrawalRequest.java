package com.westflow.seeds_manager_api.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Schema(name = "LotWithdrawalRequest", description = "Payload para registrar saída de lote")
public class LotWithdrawalRequest {

    @Schema(
            description = "ID do lote que será retirado",
            example = "15",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "O lote é obrigatório")
    private Long lotId;

    @Schema(
            description = "Número da nota fiscal de saída",
            example = "NF123456",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "O número da nota fiscal de saída é obrigatório")
    @Size(max = 50, message = "O número da nota fiscal deve ter no máximo 50 caracteres")
    @Pattern(regexp = "^[A-Z0-9-]+", message = "Número da nota fiscal contém caracteres inválidos. Use apenas letras maiúsculas, números e hífens")
    private String invoiceNumber;

    @Schema(
            description = "Quantidade retirada em kg",
            example = "250.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "A quantidade de saída é obrigatória")
    @DecimalMin(value = "0.01", message = "A quantidade deve ser maior que zero")
    @Digits(integer = 10, fraction = 2, message = "A quantidade deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
    private BigDecimal quantity;

    @Schema(
            description = "Data da saída do lote",
            example = "2025-08-17",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "A data de saída é obrigatória")
    private LocalDate withdrawalDate;

    @Schema(
            description = "Estado (UF) destino",
            example = "SP",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "O estado é obrigatório (UF)")
    @Size(min = 2, max = 2, message = "UF deve ter exatamente 2 caracteres")
    @Pattern(regexp = "^[A-Z]{2}$", message = "UF deve ser a sigla do estado com 2 letras maiúsculas")
    private String state;


    @Schema(
            description = "ID do cliente associado à saída",
            example = "7",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "O cliente é obrigatório")
    @Min(value = 1, message = "ID do cliente inválido")
    private Long clientId;
}
