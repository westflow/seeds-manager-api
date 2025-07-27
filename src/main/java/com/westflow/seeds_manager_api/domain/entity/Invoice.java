package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.enums.OperationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class Invoice {
    private Long id;
    private String invoiceNumber;
    private String producerName;
    private Seed seed;
    private BigDecimal totalKg;
    private OperationType operationType;
    private String authNumber;
    private String category;
    private BigDecimal purity;
    private String harvest;
    private String productionState;
    private BigDecimal plantedArea;
    private BigDecimal approvedArea;
    private LocalDateTime createdAt;

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
