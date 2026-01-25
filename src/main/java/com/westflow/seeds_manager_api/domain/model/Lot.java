package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.LotType;
import com.westflow.seeds_manager_api.domain.enums.SeedType;
import com.westflow.seeds_manager_api.domain.exception.BusinessException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lot {

    private Long id;
    private String lotNumber;
    private LotType lotType;
    private SeedType seedType;
    private LotCategory category;
    private BagWeight bagWeight;
    private BagType bagType;
    private BigDecimal quantityTotal;
    private BigDecimal balance;
    private String productionOrder;
    private String analysisBulletin;
    private LocalDate bulletinDate;
    private Integer hardSeeds;
    private Integer wildSeeds;
    private Integer otherCultivatedSpecies;
    private Integer tolerated;
    private Integer prohibited;
    private LocalDate validityDate;
    private Integer seedScore;
    private BigDecimal purity;
    private User user;
    private Lab lab;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;

    public static Lot newLot(
            String lotNumber,
            LotType lotType,
            SeedType seedType,
            LotCategory category,
            BagWeight bagWeight,
            BagType bagType,
            Lab lab,
            BigDecimal quantityTotal,
            String productionOrder,
            String analysisBulletin,
            LocalDate bulletinDate,
            Integer hardSeeds,
            Integer wildSeeds,
            Integer otherCultivatedSpecies,
            Integer tolerated,
            Integer prohibited,
            LocalDate validityDate,
            Integer seedScore,
            BigDecimal purity,
            User user
    ) {
        if (quantityTotal == null || quantityTotal.signum() <= 0) {
            throw new BusinessException("Quantidade total deve ser maior que zero");
        }

        Lot lot = new Lot();
        lot.id = null;
        lot.lotNumber = lotNumber;
        lot.lotType = lotType;
        lot.seedType = seedType;
        lot.category = category;
        lot.bagWeight = bagWeight;
        lot.bagType = bagType;
        lot.quantityTotal = quantityTotal;
        lot.balance = quantityTotal;
        lot.productionOrder = productionOrder;
        lot.analysisBulletin = analysisBulletin;
        lot.bulletinDate = bulletinDate;
        lot.hardSeeds = hardSeeds;
        lot.wildSeeds = wildSeeds;
        lot.otherCultivatedSpecies = otherCultivatedSpecies;
        lot.tolerated = tolerated;
        lot.prohibited = prohibited;
        lot.validityDate = validityDate;
        lot.seedScore = seedScore;
        lot.purity = purity;
        lot.user = user;
        lot.lab = lab;
        lot.createdAt = LocalDateTime.now();
        lot.updatedAt = null;
        lot.active = true;
        return lot;
    }

    public void update(
            LotType lotType,
            SeedType seedType,
            LotCategory category,
            BagWeight bagWeight,
            BagType bagType,
            Lab lab,
            BigDecimal quantityTotal,
            String productionOrder,
            String analysisBulletin,
            LocalDate bulletinDate,
            Integer hardSeeds,
            Integer wildSeeds,
            Integer otherCultivatedSpecies,
            Integer tolerated,
            Integer prohibited,
            LocalDate validityDate,
            Integer seedScore,
            BigDecimal purity,
            User user
    ) {
        if (quantityTotal == null || quantityTotal.signum() <= 0) {
            throw new BusinessException("Quantidade total deve ser maior que zero");
        }

        this.lotType = lotType;
        this.seedType = seedType;
        this.category = category;
        this.bagWeight = bagWeight;
        this.bagType = bagType;
        this.lab = lab;
        this.quantityTotal = quantityTotal;
        this.productionOrder = productionOrder;
        this.analysisBulletin = analysisBulletin;
        this.bulletinDate = bulletinDate;
        this.hardSeeds = hardSeeds;
        this.wildSeeds = wildSeeds;
        this.otherCultivatedSpecies = otherCultivatedSpecies;
        this.tolerated = tolerated;
        this.prohibited = prohibited;
        this.validityDate = validityDate;
        this.seedScore = seedScore;
        this.purity = purity;
        this.user = user;
        this.updatedAt = LocalDateTime.now();
    }

    public static Lot restore(
            Long id,
            String lotNumber,
            LotType lotType,
            SeedType seedType,
            LotCategory category,
            BagWeight bagWeight,
            BagType bagType,
            BigDecimal quantityTotal,
            BigDecimal balance,
            String productionOrder,
            String analysisBulletin,
            LocalDate bulletinDate,
            Integer hardSeeds,
            Integer wildSeeds,
            Integer otherCultivatedSpecies,
            Integer tolerated,
            Integer prohibited,
            LocalDate validityDate,
            Integer seedScore,
            BigDecimal purity,
            User user,
            Lab lab,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Boolean active
    ) {
        return new Lot(
                id,
                lotNumber,
                lotType,
                seedType,
                category,
                bagWeight,
                bagType,
                quantityTotal,
                balance,
                productionOrder,
                analysisBulletin,
                bulletinDate,
                hardSeeds,
                wildSeeds,
                otherCultivatedSpecies,
                tolerated,
                prohibited,
                validityDate,
                seedScore,
                purity,
                user,
                lab,
                createdAt,
                updatedAt,
                active
        );
    }

    public void decreaseBalance(BigDecimal quantity) {
        if (balance.compareTo(quantity) < 0) {
            throw new BusinessException("Saldo insuficiente no lote.");
        }
        this.balance = this.balance.subtract(quantity);
        this.updatedAt = LocalDateTime.now();
    }

    public void increaseBalance(BigDecimal quantity) {
        if (quantity == null || quantity.signum() <= 0) {
            throw new BusinessException("Quantidade deve ser maior que zero");
        }

        BigDecimal newBalance = this.balance.add(quantity);
        if (newBalance.compareTo(this.quantityTotal) > 0) {
            throw new BusinessException("Saldo não pode exceder a quantidade total do lote.");
        }

        this.balance = newBalance;
        this.updatedAt = LocalDateTime.now();
    }

    public void applyAllocations(List<LotInvoice> allocations) {
        allocations.forEach(a -> decreaseBalance(a.getAllocatedQuantityLot()));
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }
}