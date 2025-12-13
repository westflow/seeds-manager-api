package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class LotInvoice {

    private final Long id;
    private final Lot lot;
    private final Invoice invoice;
    private final BigDecimal allocatedQuantityLot;
    private final BigDecimal allocatedQuantityInvoice;
    private final LocalDateTime createdAt;

    public LotInvoice(Long id, Lot lot, Invoice invoice,
            BigDecimal allocatedQuantityLot, BigDecimal allocatedQuantityInvoice, LocalDateTime createdAt) {
        validate(lot, invoice, allocatedQuantityLot, allocatedQuantityInvoice);
        this.id = id;
        this.lot = lot;
        this.invoice = invoice;
        this.allocatedQuantityLot = allocatedQuantityLot;
        this.allocatedQuantityInvoice = allocatedQuantityInvoice;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    private void validate(Lot lot, Invoice invoice, BigDecimal allocatedQuantityLot, BigDecimal allocatedQuantityInvoice) {
        if (lot == null) {
            throw new ValidationException("Lote é obrigatório");
        }

        if (invoice == null) {
            throw new ValidationException("Nota fiscal é obrigatória");
        }

        if (allocatedQuantityLot == null || allocatedQuantityLot.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Quantidade alocada deve ser maior que zero");
        }

        if (allocatedQuantityInvoice == null || allocatedQuantityInvoice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Quantidade alocada na nota fiscal deve ser maior que zero");
        }
    }
}