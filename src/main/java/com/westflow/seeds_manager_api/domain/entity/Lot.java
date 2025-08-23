package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.LotType;
import com.westflow.seeds_manager_api.domain.enums.SeedType;
import com.westflow.seeds_manager_api.domain.exception.InsufficientLotBalanceException;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Lot {

    private final Long id;
    private final String lotNumber;
    private final LotType lotType;
    private final Seed seed;
    private final SeedType seedType;
    private final LotCategory category;
    private final BigDecimal bagWeight;
    private BigDecimal balance;
    private final String analysisBulletin;
    private final LocalDate bulletinDate;
    private final Invoice invoice;
    private final String bagType;
    private final LocalDate validityDate;
    private final Integer seedScore;
    private final User user;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public Lot(Long id, String lotNumber, LotType lotType, Seed seed, SeedType seedType,
               LotCategory category, BigDecimal bagWeight, BigDecimal balance,
               String analysisBulletin, LocalDate bulletinDate, Invoice invoice,
               String bagType, LocalDate validityDate, Integer seedScore,
               User user, LocalDateTime createdAt, LocalDateTime updatedAt) {

        validate(lotNumber, lotType, seed, seedType, category, bagWeight, balance, invoice, bagType);

        this.id = id;
        this.lotNumber = lotNumber;
        this.lotType = lotType;
        this.seed = seed;
        this.seedType = seedType;
        this.category = category;
        this.bagWeight = bagWeight;
        this.balance = balance;
        this.analysisBulletin = analysisBulletin;
        this.bulletinDate = bulletinDate;
        this.invoice = invoice;
        this.bagType = bagType;
        this.validityDate = validityDate;
        this.seedScore = seedScore;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void validate(String lotNumber, LotType lotType, Seed seed, SeedType seedType,
                          LotCategory category, BigDecimal bagWeight, BigDecimal balance,
                          Invoice invoice, String bagType) {

        if (lotNumber == null || lotNumber.isBlank()) {
            throw new ValidationException("Número do lote é obrigatório");
        }

        if (lotType == null) {
            throw new ValidationException("Tipo de lote é obrigatório");
        }

        if (seed == null) {
            throw new ValidationException("Semente é obrigatória");
        }

        if (seedType == null) {
            throw new ValidationException("Tipo de semente é obrigatório");
        }

        if (category == null) {
            throw new ValidationException("Categoria é obrigatória");
        }

        if (bagWeight == null || bagWeight.compareTo(BigDecimal.valueOf(0.01)) < 0) {
            throw new ValidationException("Peso da sacaria deve ser maior que zero");
        }

        if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Saldo do lote não pode ser negativo");
        }

        if (invoice == null) {
            throw new ValidationException("Nota fiscal é obrigatória");
        }

        if (bagType == null || bagType.isBlank()) {
            throw new ValidationException("Tipo de sacaria é obrigatório");
        }
    }

    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Quantidade de retirada deve ser maior que zero");
        }

        if (balance.compareTo(amount) < 0) {
            throw new InsufficientLotBalanceException(lotNumber, balance.doubleValue(), amount.doubleValue());
        }

        this.balance = this.balance.subtract(amount);
    }
}
