package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.enums.OperationType;
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
    private final String category;
    private final BigDecimal purity;
    private final String harvest;
    private final String productionState;
    private final BigDecimal plantedArea;
    private final BigDecimal approvedArea;
    private final LocalDateTime createdAt;

    public Invoice(Long id, String invoiceNumber, String producerName, Seed seed,
                   BigDecimal totalKg, OperationType operationType, String authNumber,
                   String category, BigDecimal purity, String harvest,
                   String productionState, BigDecimal plantedArea,
                   BigDecimal approvedArea, LocalDateTime createdAt) {

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
    }
}
