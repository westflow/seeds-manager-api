package com.westflow.seeds_manager_api.infrastructure.persistence.entity;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.LotType;
import com.westflow.seeds_manager_api.domain.enums.SeedType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lots")
public class LotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lot_number", nullable = false, unique = true)
    private String lotNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "lot_type", nullable = false)
    private LotType lotType;

    @Enumerated(EnumType.STRING)
    @Column(name = "seed_type", nullable = false)
    private SeedType seedType;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private LotCategory category;

    @ManyToOne
    @JoinColumn(name = "bag_weight_id", nullable = false)
    private BagWeightEntity bagWeight;

    @ManyToOne
    @JoinColumn(name = "bag_type_id", nullable = false)
    private BagTypeEntity bagType;

    @Column(name = "quantity_total", nullable = false)
    private BigDecimal quantityTotal;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "production_order")
    private String productionOrder;

    @Column(name = "analysis_bulletin")
    private String analysisBulletin;

    @Column(name = "bulletin_date")
    private LocalDate bulletinDate;

    @Column(name = "hard_seeds", nullable = false)
    private Integer hardSeeds = 0;

    @Column(name = "wild_seeds", nullable = false)
    private Integer wildSeeds = 0;

    @Column(name = "other_cultivated_species", nullable = false)
    private Integer otherCultivatedSpecies = 0;

    @Column(name = "tolerated", nullable = false)
    private Integer tolerated = 0;

    @Column(name = "prohibited", nullable = false)
    private Integer prohibited = 0;

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LotInvoiceEntity> lotInvoices = new ArrayList<>();

    @Column(name = "validity_date")
    private LocalDate validityDate;

    @Column(name = "seed_score")
    private Integer seedScore;
    @Column(name = "purity", nullable = false)
    private BigDecimal purity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "lab_id", nullable = false)
    private LabEntity lab;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
