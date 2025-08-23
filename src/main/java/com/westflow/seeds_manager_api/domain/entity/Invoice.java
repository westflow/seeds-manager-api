package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.OperationType;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class Invoice {

    private final Long id;
    private final String invoiceNumber;
    private final String producerName;
    private final Seed seed;
    private final BigDecimal totalKg;
    private final OperationType operationType;
    private final String authNumber;
    private final LotCategory category;
    private final BigDecimal purity;
    private final String harvest;
    private final String productionState;
    private final BigDecimal plantedArea;
    private final BigDecimal approvedArea;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public Invoice(Long id, String invoiceNumber, String producerName, Seed seed,
                   BigDecimal totalKg, OperationType operationType, String authNumber,
                   LotCategory category, BigDecimal purity, String harvest,
                   String productionState, BigDecimal plantedArea,
                   BigDecimal approvedArea, LocalDateTime createdAt, LocalDateTime updatedAt) {

        validate(invoiceNumber, producerName, seed, totalKg, operationType, category, purity, harvest, productionState);

        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.producerName = producerName;
        this.seed = seed;
        this.totalKg = totalKg;
        this.operationType = operationType;
        this.authNumber = authNumber;
        this.category = category;
        this.purity = purity;
        this.harvest = harvest;
        this.productionState = productionState;
        this.plantedArea = plantedArea;
        this.approvedArea = approvedArea;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void validate(String invoiceNumber, String producerName, Seed seed,
                          BigDecimal totalKg, OperationType operationType,
                          LotCategory category, BigDecimal purity,
                          String harvest, String productionState) {

        if (invoiceNumber == null || invoiceNumber.isBlank()) {
            throw new ValidationException("Número da nota é obrigatório");
        }

        if (producerName == null || producerName.isBlank()) {
            throw new ValidationException("Nome do produtor é obrigatório");
        }

        if (seed == null) {
            throw new ValidationException("Semente é obrigatória");
        }

        if (totalKg == null || totalKg.compareTo(BigDecimal.valueOf(0.01)) < 0) {
            throw new ValidationException("Peso total deve ser maior que zero");
        }

        if (operationType == null) {
            throw new ValidationException("Tipo de operação é obrigatório");
        }

        if (category == null) {
            throw new ValidationException("Categoria é obrigatória");
        }

        if (purity == null || purity.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Pureza é obrigatória e deve ser não negativa");
        }

        if (harvest == null || harvest.isBlank()) {
            throw new ValidationException("Safra é obrigatória");
        }

        if (productionState == null || productionState.isBlank()) {
            throw new ValidationException("UF de produção é obrigatória");
        }
    }
}
