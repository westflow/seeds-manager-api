package com.westflow.seeds_manager_api.infrastructure.persistence.entity;

import com.westflow.seeds_manager_api.domain.enums.LotStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lot_reservations")
public class LotReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private LotEntity lot;

    private BigDecimal quantity;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Enumerated(EnumType.STRING)
    private LotStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
