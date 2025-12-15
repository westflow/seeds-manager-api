package com.westflow.seeds_manager_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LotInvoice {

    private Long id;
    private Lot lot;
    private Invoice invoice;
    private BigDecimal allocatedQuantityLot;
    private BigDecimal allocatedQuantityInvoice;
    private LocalDateTime createdAt;

    public static LotInvoice create(
            Lot lot,
            Invoice invoice,
            BigDecimal quantityLot,
            BigDecimal quantityInvoice
    ) {
        return LotInvoice.builder()
                .lot(lot)
                .invoice(invoice)
                .allocatedQuantityLot(quantityLot)
                .allocatedQuantityInvoice(quantityInvoice)
                .createdAt(LocalDateTime.now())
                .build();
    }
}