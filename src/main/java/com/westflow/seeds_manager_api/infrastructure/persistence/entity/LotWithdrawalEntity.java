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
    @JoinColumn(name = "lot_id")
    private LotEntity lot;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    private BigDecimal quantity;

    @Column(name = "withdrawal_date")
    private LocalDate withdrawalDate;

    private String state;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
