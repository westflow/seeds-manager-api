package com.westflow.seeds_manager_api.domain.entity;

import com.westflow.seeds_manager_api.domain.enums.LotStatus;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class LotReservation {

    private final Long id;
    private final Lot lot;
    private final BigDecimal quantity;
    private final LocalDate reservationDate;
    private LotStatus status;
    private final Client client;
    private final User user;

    public LotReservation(Long id, Lot lot, BigDecimal quantity, LocalDate reservationDate,
                          LotStatus status, Client client, User user) {

        if (lot == null || client == null || user == null)
            throw new ValidationException("Lot, client and user must be specified");

        if (reservationDate == null)
            throw new ValidationException("Reservation date must not be null");

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0)
            throw new ValidationException("Quantity must be greater than zero");

        this.id = id;
        this.lot = lot;
        this.quantity = quantity;
        this.reservationDate = reservationDate;
        this.status = status;
        this.client = client;
        this.user = user;
    }

    public void confirm() {
        if (status != LotStatus.PENDING) {
            throw new ValidationException("Only pending reservations can be confirmed");
        }
        status = LotStatus.CONFIRMED;
    }

    public void cancel() {
        if (status == LotStatus.CANCELLED) {
            throw new ValidationException("Reservation is already cancelled");
        }
        status = LotStatus.CANCELLED;
    }
}
