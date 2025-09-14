package com.westflow.seeds_manager_api.infrastructure.persistence.entity;

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
@Table(name = "lot_invoices")
public class LotInvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lot_id", nullable = false)
    private LotEntity lot;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private InvoiceEntity invoice;

    @Column(name = "allocated_quantity_lot", nullable = false)
    private BigDecimal allocatedQuantityLot;

    @Column(name = "allocated_quantity_invoice", nullable = false)
    private BigDecimal allocatedQuantityInvoice;

    private LocalDateTime createdAt = LocalDateTime.now();
}
