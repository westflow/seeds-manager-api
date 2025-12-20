package com.westflow.seeds_manager_api.infrastructure.persistence.entity;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;

    @Column(name = "producer_name", nullable = false)
    private String producerName;

    @ManyToOne
    @JoinColumn(name = "seed_id", nullable = false)
    private SeedEntity seed;

    @Column(name = "total_kg", nullable = false)
    private BigDecimal totalKg;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    private OperationType operationType;

    @Column(name = "auth_number")
    private String authNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private LotCategory category;
    @Column(name = "purity", nullable = false)
    private BigDecimal purity;
    @Column(name = "harvest", nullable = false)
    private String harvest;

    @Column(name = "production_state", nullable = false)
    private String productionState;

    @Column(name = "planted_area")
    private BigDecimal plantedArea;

    @Column(name = "approved_area")
    private BigDecimal approvedArea;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "active", nullable = false)
    private Boolean active = true;
}
