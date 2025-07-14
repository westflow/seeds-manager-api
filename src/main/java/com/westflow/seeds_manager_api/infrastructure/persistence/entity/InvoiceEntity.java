package com.westflow.seeds_manager_api.infrastructure.persistence.entity;

import com.westflow.seeds_manager_api.domain.enums.OperationType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "producer_name")
    private String producerName;

    @ManyToOne
    @JoinColumn(name = "cultivar_id")
    private SeedEntity cultivar;

    @Column(name = "total_kg")
    private BigDecimal totalKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;

    @Column(name = "auth_number")
    private String authNumber;

    private String category;
    private BigDecimal purity;
    private String harvest;

    @Column(name = "production_state")
    private String productionState;

    @Column(name = "planted_area")
    private BigDecimal plantedArea;

    @Column(name = "approved_area")
    private BigDecimal approvedArea;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
