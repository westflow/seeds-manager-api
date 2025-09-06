package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class LotInvoice {

    private final Long id;
    private final Lot lot;
    private final Invoice invoice;
    private final BigDecimal allocatedQuantity;
    private final LocalDateTime createdAt;

    public LotInvoice(Long id, Lot lot, Invoice invoice, BigDecimal allocatedQuantity, LocalDateTime createdAt) {
        validate(lot, invoice, allocatedQuantity);
        this.id = id;
        this.lot = lot;
        this.invoice = invoice;
        this.allocatedQuantity = allocatedQuantity;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    private void validate(Lot lot, Invoice invoice, BigDecimal allocatedQuantity) {
        if (lot == null) {
            throw new ValidationException("Lote é obrigatório");
        }

        if (invoice == null) {
            throw new ValidationException("Nota fiscal é obrigatória");
        }

        if (allocatedQuantity == null || allocatedQuantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Quantidade alocada deve ser maior que zero");
        }

        if (allocatedQuantity.compareTo(invoice.getBalance()) > 0) {
            throw new ValidationException("Quantidade alocada excede o saldo da nota fiscal");
        }
    }
}