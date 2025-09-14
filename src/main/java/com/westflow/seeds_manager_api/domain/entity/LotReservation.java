package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.enums.LotStatus;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class LotReservation {

    private final Long id;
    private final Lot lot;
    private final String identification;
    private final BigDecimal quantity;
    private final LocalDateTime reservationDate;
    private LotStatus status;
    private final User user;
    private final Client client;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public LotReservation(Long id, Lot lot, String identification, BigDecimal quantity,
                          LocalDateTime reservationDate, LotStatus status, Client client, User user,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        validate(lot, quantity, reservationDate, status, user);

        this.id = id;
        this.lot = lot;
        this.identification = identification;
        this.quantity = quantity;
        this.reservationDate = reservationDate;
        this.status = status;
        this.client = client;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void validate(Lot lot, BigDecimal quantity, LocalDateTime reservationDate,
                          LotStatus status, User user) {
        if (lot == null) {
            throw new ValidationException("O lote deve ser informado");
        }

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("A quantidade deve ser maior que zero");
        }

        if (reservationDate == null) {
            throw new ValidationException("A data da reserva não pode ser nula");
        }

        if (status == null) {
            throw new ValidationException("O status da reserva é obrigatório");
        }

        if (user == null) {
            throw new ValidationException("O usuário deve ser informado");
        }
    }

    public void cancel() {
        if (status == LotStatus.CANCELLED)
            throw new ValidationException("A reserva já está cancelada");
        this.status = LotStatus.CANCELLED;
    }
}
