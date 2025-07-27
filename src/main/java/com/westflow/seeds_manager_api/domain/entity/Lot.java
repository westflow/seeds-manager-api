package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.LotType;
import com.westflow.seeds_manager_api.domain.enums.SeedType;
import com.westflow.seeds_manager_api.domain.exception.InsufficientLotBalanceException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class Lot {
    private Long id;
    private String lotNumber;
    private LotType lotType;
    private Seed seed;
    private SeedType seedType;
    private LotCategory category;
    private BigDecimal bagWeight;
    private BigDecimal balance;
    private String analysisBulletin;
    private LocalDate bulletinDate;
    private Invoice invoice;
    private String bagType;
    private LocalDate validityDate;
    private Integer seedScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
