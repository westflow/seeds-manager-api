package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.OperationType;
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
public class Invoice {

    private Long id;
    private String invoiceNumber;
    private String producerName;
    private Seed seed;
    private BigDecimal totalKg;
    private BigDecimal balance;
    private OperationType operationType;
    private String authNumber;
    private LotCategory category;
    private BigDecimal purity;
    private String harvest;
    private String productionState;
    private BigDecimal plantedArea;
    private BigDecimal approvedArea;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;

    public static Invoice newInvoice(
            String invoiceNumber,
            String producerName,
            Seed seed,
            BigDecimal totalKg,
            OperationType operationType,
            String authNumber,
            LotCategory category,
            BigDecimal purity,
            String harvest,
            String productionState,
            BigDecimal plantedArea,
            BigDecimal approvedArea
    ) {
        validate(invoiceNumber, producerName, seed, totalKg, operationType, category, purity, harvest, productionState);

        Invoice invoice = new Invoice();
        invoice.id = null;
        invoice.invoiceNumber = invoiceNumber;
        invoice.producerName = producerName;
        invoice.seed = seed;
        invoice.totalKg = totalKg;
        invoice.balance = totalKg;
        invoice.operationType = operationType;
        invoice.authNumber = authNumber;
        invoice.category = category;
        invoice.purity = purity;
        invoice.harvest = harvest;
        invoice.productionState = productionState;
        invoice.plantedArea = plantedArea;
        invoice.approvedArea = approvedArea;
        invoice.createdAt = LocalDateTime.now();
        invoice.updatedAt = null;
        invoice.active = true;
        return invoice;
    }

    public static Invoice restore(
            Long id,
            String invoiceNumber,
            String producerName,
            Seed seed,
            BigDecimal totalKg,
            BigDecimal balance,
            OperationType operationType,
            String authNumber,
            LotCategory category,
            BigDecimal purity,
            String harvest,
            String productionState,
            BigDecimal plantedArea,
            BigDecimal approvedArea,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Boolean active
    ) {
        validate(invoiceNumber, producerName, seed, totalKg, operationType, category, purity, harvest, productionState);

        return new Invoice(
                id,
                invoiceNumber,
                producerName,
                seed,
                totalKg,
                balance,
                operationType,
                authNumber,
                category,
                purity,
                harvest,
                productionState,
                plantedArea,
                approvedArea,
                createdAt,
                updatedAt,
                active
        );
    }

    public void update(
            String invoiceNumber,
            String producerName,
            Seed seed,
            BigDecimal totalKg,
            OperationType operationType,
            String authNumber,
            LotCategory category,
            BigDecimal purity,
            String harvest,
            String productionState,
            BigDecimal plantedArea,
            BigDecimal approvedArea
    ) {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new ValidationException("Nota fiscal está inativa e não pode ser atualizada.");
        }

        validate(invoiceNumber, producerName, seed, totalKg, operationType, category, purity, harvest, productionState);

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
        this.updatedAt = LocalDateTime.now();
    }

    private static void validate(String invoiceNumber, String producerName, Seed seed,
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

        if (purity == null || purity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Pureza é obrigatória e deve ser positiva");
        }

        if (harvest == null || harvest.isBlank()) {
            throw new ValidationException("Safra é obrigatória");
        }

        if (productionState == null || productionState.isBlank()) {
            throw new ValidationException("UF de produção é obrigatória");
        }
    }

    public void withUpdatedBalance(BigDecimal allocated) {
        if (allocated == null || allocated.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Quantidade alocada deve ser maior que zero");
        }
        if (this.balance.compareTo(allocated) < 0) {
            throw new ValidationException("Quantidade alocada excede o saldo da nota fiscal " + this.invoiceNumber);
        }
        this.balance = this.balance.subtract(allocated);
    }

    public void restoreBalance(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Quantidade para devolver deve ser maior que zero");
        }
        this.balance = this.balance.add(amount);
    }

    public void deactivate() {
        if (!this.active) {
            throw new ValidationException("Nota fiscal já está inativa");
        }
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }
}
