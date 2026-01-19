package com.westflow.seeds_manager_api.domain.service;

import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotInvoice;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LotDomainService {

    public void validateInvoices(
            Lot lot,
            List<Invoice> invoices,
            Map<Long, BigDecimal> allocationMap
    ) {
        validateLotMustHaveInvoices(allocationMap);
        validateAllocationQuantities(allocationMap);
        validateInvoicesExist(invoices, allocationMap);
        validateTotalAllocation(lot.getQuantityTotal(), allocationMap);
        validatePurity(lot.getPurity());
    }

    public void validateUpdateAllowed(
            boolean hasWithdrawals,
            boolean hasReservations
    ) {
        if (hasWithdrawals || hasReservations) {
            throw new BusinessException(
                    "Não é permitido alterar o lote pois já existem saídas ou reservas associadas."
            );
        }
    }

    public List<LotInvoice> createAllocations(
            Lot lot,
            List<Invoice> invoices,
            Map<Long, BigDecimal> allocationMap
    ) {
        List<LotInvoice> allocations = new ArrayList<>();

        for (Invoice invoice : invoices) {
            BigDecimal invoiceQuantity = allocationMap.get(invoice.getId());

            if (invoiceQuantity == null || invoiceQuantity.signum() <= 0) {
                continue;
            }

            BigDecimal quantityInLot = applyPurity(
                    invoiceQuantity,
                    lot.getPurity(),
                    invoice.getPurity()
            );

            allocations.add(
                    LotInvoice.newLotInvoice(
                            lot,
                            invoice,
                            invoiceQuantity,
                            quantityInLot
                    )
            );
        }

        return allocations;
    }


    private void validateLotMustHaveInvoices(
            Map<Long, BigDecimal> allocationMap
    ) {
        if (allocationMap == null || allocationMap.isEmpty()) {
            throw new BusinessException(
                    "Não é permitido criar ou atualizar um lote sem notas fiscais associadas."
            );
        }
    }

    private void validateAllocationQuantities(
            Map<Long, BigDecimal> allocationMap
    ) {
        if (allocationMap == null || allocationMap.isEmpty()) {
            return;
        }

        allocationMap.forEach((invoiceId, quantity) -> {
            if (quantity == null || quantity.signum() <= 0) {
                throw new BusinessException(
                        "Quantidade inválida informada para a nota fiscal ID: " + invoiceId
                );
            }
        });
    }

    private void validateInvoicesExist(
            List<Invoice> invoices,
            Map<Long, BigDecimal> allocationMap
    ) {
        if (allocationMap.isEmpty()) return;

        if (invoices.size() != allocationMap.size()) {
            throw new BusinessException(
                    "Uma ou mais notas fiscais informadas não foram encontradas."
            );
        }
    }

    private void validateTotalAllocation(
            BigDecimal lotTotalQuantity,
            Map<Long, BigDecimal> allocationMap
    ) {
        if (allocationMap.isEmpty()) return;

        BigDecimal totalAllocated = allocationMap.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalAllocated.signum() <= 0) {
            throw new BusinessException(
                    "A soma das quantidades das notas fiscais deve ser maior que zero."
            );
        }

        if (totalAllocated.compareTo(lotTotalQuantity) > 0) {
            throw new BusinessException(
                    "A soma das quantidades das notas fiscais não pode ser maior que a quantidade total do lote."
            );
        }
    }

    private void validatePurity(BigDecimal purity) {
        if (purity == null) {
            throw new BusinessException("Pureza do lote é obrigatória.");
        }

        if (purity.compareTo(BigDecimal.ZERO) <= 0
                || purity.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new BusinessException(
                    "Pureza deve estar entre 0 e 100."
            );
        }
    }

    private BigDecimal applyPurity(
            BigDecimal quantity,
            BigDecimal lotPurity,
            BigDecimal invoicePurity
    ) {
        return quantity
                .multiply(lotPurity)
                .divide(invoicePurity, 2, RoundingMode.HALF_UP);
    }
}
