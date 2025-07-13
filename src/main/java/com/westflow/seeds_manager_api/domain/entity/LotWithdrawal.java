package com.westflow.seeds_manager_api.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "withdrawals")
public class LotWithdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    private BigDecimal quantity;
    private LocalDate withdrawalDate;
    private String state;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
