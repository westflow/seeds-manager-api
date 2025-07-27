package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
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
public class LotWithdrawal {

    private Long id;
    private Lot lot;
    private String invoiceNumber;
    private BigDecimal quantity;
    private LocalDate withdrawalDate;
    private String state;
    private Client client;
    private LocalDateTime createdAt;

    public LotWithdrawal(Long id, Lot lot, String invoiceNumber, BigDecimal quantity,
                         LocalDate withdrawalDate, String state, Client client, LocalDateTime createdAt) {

        if (lot == null || quantity == null || withdrawalDate == null)
            throw new ValidationException("Lot, quantity and withdrawal date are required");

        if (quantity.compareTo(BigDecimal.ZERO) <= 0)
            throw new ValidationException("Withdrawal quantity must be positive");

        this.id = id;
        this.lot = lot;
        this.invoiceNumber = invoiceNumber;
        this.quantity = quantity;
        this.withdrawalDate = withdrawalDate;
        this.state = state;
        this.client = client;
        this.createdAt = createdAt;

        lot.withdraw(quantity);
    }
}
