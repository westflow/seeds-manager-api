package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LotInvoice {

    private Long id;
    private Lot lot;
    private Invoice invoice;
    private BigDecimal allocatedQuantityLot;
    private BigDecimal allocatedQuantityInvoice;
    private LocalDateTime createdAt;

    public static LotInvoice newLotInvoice(
            Lot lot,
            Invoice invoice,
            BigDecimal quantityLot,
            BigDecimal quantityInvoice
    ) {
        validate(lot, invoice, quantityLot, quantityInvoice);

        LotInvoice lotInvoice = new LotInvoice();
        lotInvoice.id = null;
        lotInvoice.lot = lot;
        lotInvoice.invoice = invoice;
        lotInvoice.allocatedQuantityLot = quantityLot;
        lotInvoice.allocatedQuantityInvoice = quantityInvoice;
        lotInvoice.createdAt = LocalDateTime.now();
        return lotInvoice;
    }

    public static LotInvoice restore(
            Long id,
            Lot lot,
            Invoice invoice,
            BigDecimal quantityLot,
            BigDecimal quantityInvoice,
            LocalDateTime createdAt
    ) {
        validate(lot, invoice, quantityLot, quantityInvoice);

        LotInvoice lotInvoice = new LotInvoice();
        lotInvoice.id = id;
        lotInvoice.lot = lot;
        lotInvoice.invoice = invoice;
        lotInvoice.allocatedQuantityLot = quantityLot;
        lotInvoice.allocatedQuantityInvoice = quantityInvoice;
        lotInvoice.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        return lotInvoice;
    }

    private static void validate(
            Lot lot,
            Invoice invoice,
            BigDecimal quantityLot,
            BigDecimal quantityInvoice
    ) {
        if (lot == null) {
            throw new ValidationException("Lote é obrigatório para alocação da nota.");
        }
        if (invoice == null) {
            throw new ValidationException("Nota fiscal é obrigatória para alocação do lote.");
        }
        if (quantityLot == null || quantityLot.signum() <= 0) {
            throw new ValidationException("Quantidade alocada no lote deve ser positiva.");
        }
        if (quantityInvoice == null || quantityInvoice.signum() <= 0) {
            throw new ValidationException("Quantidade alocada na nota deve ser positiva.");
        }
    }
}
