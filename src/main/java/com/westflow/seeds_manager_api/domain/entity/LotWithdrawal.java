package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class LotWithdrawal {

    private final Long id;
    private final Lot lot;
    private final String invoiceNumber;
    private final BigDecimal quantity;
    private final LocalDate withdrawalDate;
    private final String state;
    private final User user;
    private final Client client;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public LotWithdrawal(Long id, Lot lot, String invoiceNumber,
                         BigDecimal quantity, LocalDate withdrawalDate, String state,
                         User user, Client client, LocalDateTime createdAt, LocalDateTime updatedAt) {

        validate(lot, invoiceNumber, quantity, withdrawalDate, state, user, client);

        this.id = id;
        this.lot = lot;
        this.invoiceNumber = invoiceNumber;
        this.quantity = quantity;
        this.withdrawalDate = withdrawalDate;
        this.state = state;
        this.user = user;
        this.client = client;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void validate(Lot lot, String invoiceNumber, BigDecimal quantity, LocalDate withdrawalDate, String state, User user, Client client) {
        if (lot == null) {
            throw new ValidationException("O lote deve ser informado");
        }

        if (user == null) {
            throw new ValidationException("O usuário deve ser informado");
        }

        if (invoiceNumber == null || invoiceNumber.isBlank()) {
            throw new ValidationException("O número da nota fiscal de saída é obrigatório");
        }

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("A quantidade deve ser maior que zero");
        }

        if (withdrawalDate == null) {
            throw new ValidationException("A data de saída é obrigatória");
        }

        if (state == null || state.isBlank()) {
            throw new ValidationException("O estado (UF) é obrigatório");
        }

        if (client == null) {
            throw new ValidationException("O cliente associado à saída é obrigatório");
        }
    }
}
