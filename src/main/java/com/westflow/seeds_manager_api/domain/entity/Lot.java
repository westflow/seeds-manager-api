package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.LotType;
import com.westflow.seeds_manager_api.domain.enums.SeedType;
import com.westflow.seeds_manager_api.domain.exception.InsufficientLotBalanceException;
import lombok.Getter;

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
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Lot(Long id, String lotNumber, LotType lotType, Seed seed, SeedType seedType,
               LotCategory category, BigDecimal bagWeight, BigDecimal balance,
               String analysisBulletin, LocalDate bulletinDate, Invoice invoice,
               String bagType, LocalDate validityDate, Integer seedScore,
               LocalDateTime createdAt, LocalDateTime updatedAt) {

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
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0)
            throw new InsufficientLotBalanceException(lotNumber, balance.doubleValue(), amount.doubleValue());
        this.balance = this.balance.subtract(amount);
    }
}
