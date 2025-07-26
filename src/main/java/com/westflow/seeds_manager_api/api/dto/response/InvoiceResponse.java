package com.westflow.seeds_manager_api.api.dto.response;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {

    private Long id;
    private String invoiceNumber;
    private String producerName;
    private Long seedId;
    private BigDecimal totalKg;
    private OperationType operationType;
    private String authNumber;
    private LotCategory category;
    private BigDecimal purity;
    private String harvest;
    private String productionState;
    private BigDecimal plantedArea;
    private BigDecimal approvedArea;
}
