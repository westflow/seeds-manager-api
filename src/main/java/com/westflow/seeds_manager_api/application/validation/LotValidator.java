package com.westflow.seeds_manager_api.application.validation;

import com.westflow.seeds_manager_api.api.dto.request.LotRequest;
import com.westflow.seeds_manager_api.domain.entity.Invoice;
import com.westflow.seeds_manager_api.domain.enums.OperationType;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class LotValidator {
    public void validateInvoices(List<Invoice> invoices, LotRequest request, Map<Long, BigDecimal> allocationMap) {
        if (invoices.isEmpty()) {
            throw new ValidationException("É necessário informar ao menos uma nota fiscal.");
        }

        if (invoices.size() > 1 &&
                invoices.stream().anyMatch(i -> i.getOperationType() == OperationType.REPACKAGING)
        ) {
            throw new BusinessException("Notas fiscais com o tipo de operação reembalo devem conter exatamente uma nota fiscal.");
        }

        boolean sameSeed  = invoices.stream()
                .map(i -> i.getSeed().getId())
                .distinct()
                .count() == 1;

        if (!sameSeed ) {
            throw new BusinessException("Todas as notas fiscais devem conter a mesma semente.");
        }

        boolean sameHarvest = invoices.stream()
                .map(Invoice::getHarvest)
                .distinct()
                .count() == 1;

        if (!sameHarvest) {
            throw new BusinessException("Todas as notas fiscais devem pertencer à mesma safra.");
        }

        if (invoices.stream().anyMatch(i ->
                i.getOperationType() == OperationType.REPACKAGING &&
                        i.getPurity().compareTo(request.getPurity()) != 0
        )) {
            throw new BusinessException("Notas com tipo de operação de reembalo: a pureza deve ser a mesma da nota fiscal.");
        }

        BigDecimal totalAllocated = allocationMap.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalAllocated.compareTo(request.getQuantityTotal()) != 0) {
            throw new BusinessException("A soma das quantidades alocadas não corresponde à quantidade total do lote.");
        }
    }
}

