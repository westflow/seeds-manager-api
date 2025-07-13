package com.westflow.seeds_manager_api.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lots")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lot_number", unique = true)
    private String lotNumber;

    private String lotType;

    @ManyToOne
    @JoinColumn(name = "cultivar_id")
    private Seed cultivar;

    private String seedType;
    private String category;
    private BigDecimal bagWeight;
    private BigDecimal balance;
    private String analysisBulletin;
    private LocalDate bulletinDate;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    private String bagType;
    private LocalDate validityDate;
    private Integer seedScore;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
