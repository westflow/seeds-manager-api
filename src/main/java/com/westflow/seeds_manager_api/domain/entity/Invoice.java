package com.westflow.seeds_manager_api.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "producer_name")
    private String producerName;

    @ManyToOne
    @JoinColumn(name = "cultivar_id")
    private Seed cultivar;

    private BigDecimal totalKg;
    private String operationType;
    private String authNumber;
    private String category;
    private BigDecimal purity;
    private String harvest;
    private String productionState;
    private BigDecimal plantedArea;
    private BigDecimal approvedArea;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
