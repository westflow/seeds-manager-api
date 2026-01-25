package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LotWithdrawal {

    private Long id;
    private Lot lot;
    private String invoiceNumber;
    private BigDecimal quantity;
    private LocalDate withdrawalDate;
    private String state;
    private User user;
    private Client client;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;

    public static LotWithdrawal newWithdrawal(
            Lot lot,
            String invoiceNumber,
            BigDecimal quantity,
            LocalDate withdrawalDate,
            String state,
            User user,
            Client client
    ) {
        validate(lot, invoiceNumber, quantity, withdrawalDate, state, user, client);

        return new LotWithdrawal(
                null,
                lot,
                invoiceNumber,
                quantity,
                withdrawalDate,
                state,
                user,
                client,
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
        );
    }

    public static LotWithdrawal restore(
            Long id,
            Lot lot,
            String invoiceNumber,
            BigDecimal quantity,
            LocalDate withdrawalDate,
            String state,
            User user,
            Client client,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Boolean active
    ) {
        return new LotWithdrawal(
                id,
                lot,
                invoiceNumber,
                quantity,
                withdrawalDate,
                state,
                user,
                client,
                createdAt,
                updatedAt,
                active
        );
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    private static void validate(Lot lot, String invoiceNumber, BigDecimal quantity, LocalDate withdrawalDate, String state, User user, Client client) {
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
