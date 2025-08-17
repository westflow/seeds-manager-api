package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.LotType;
import com.westflow.seeds_manager_api.domain.enums.SeedType;
import com.westflow.seeds_manager_api.domain.exception.InsufficientLotBalanceException;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0)
            throw new InsufficientLotBalanceException(lotNumber, balance.doubleValue(), amount.doubleValue());
        this.balance = this.balance.subtract(amount);
    }
}
