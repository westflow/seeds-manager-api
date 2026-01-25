package com.westflow.seeds_manager_api.domain.model;

import com.westflow.seeds_manager_api.domain.enums.LotStatus;
import com.westflow.seeds_manager_api.domain.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LotReservation {

    private Long id;
    private Lot lot;
    private String identification;
    private BigDecimal quantity;
    private LocalDateTime reservationDate;
    private LotStatus status;
    private User user;
    private Client client;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static LotReservation newReservation(Lot lot, String identification, BigDecimal quantity,
                                               User user, Client client) {
        LocalDateTime now = LocalDateTime.now();
        LotReservation reservation = new LotReservation(
                null,
                lot,
                identification,
                quantity,
                now,
                LotStatus.RESERVED,
                user,
                client,
                now,
                null
        );

        reservation.validate(lot, quantity, reservation.getReservationDate(), reservation.getStatus(), user);

        return reservation;
    }

    public static LotReservation restore(Long id, Lot lot, String identification, BigDecimal quantity,
                                         LocalDateTime reservationDate, LotStatus status, Client client, User user,
                                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        LotReservation reservation = new LotReservation(
                id,
                lot,
                identification,
                quantity,
                reservationDate,
                status,
                user,
                client,
                createdAt,
                updatedAt
        );

        reservation.validate(lot, quantity, reservationDate, status, user);

        return reservation;
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
        if (this.status != LotStatus.RESERVED) {
            throw new ValidationException("Reserva não pode ser cancelada");
        }

        this.status = LotStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }
}
