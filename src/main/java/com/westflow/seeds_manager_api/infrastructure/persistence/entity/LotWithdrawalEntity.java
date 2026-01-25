package com.westflow.seeds_manager_api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lot_withdrawals")
public class LotWithdrawalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lot_id",  nullable = false)
    private LotEntity lot;

    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;


    @Column(name = "withdrawal_date", nullable = false)
    private LocalDate withdrawalDate;

    @Column(name = "state", nullable = false)
    private String state;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "active", nullable = false)
    private boolean active = true;
}
