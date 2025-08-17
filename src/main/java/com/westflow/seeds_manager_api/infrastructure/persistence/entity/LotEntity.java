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

    @Column(name = "lot_number", unique = true)
    private String lotNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "lot_type")
    private LotType lotType;

    @ManyToOne
    @JoinColumn(name = "seed_id")
    private SeedEntity seed;

    @Enumerated(EnumType.STRING)
    @Column(name = "seed_type")
    private SeedType seedType;

    @Enumerated(EnumType.STRING)
    private LotCategory category;

    @Column(name = "bag_weight")
    private BigDecimal bagWeight;

    private BigDecimal balance;

    @Column(name = "analysis_bulletin")
    private String analysisBulletin;

    @Column(name = "bulletin_date")
    private LocalDate bulletinDate;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private InvoiceEntity invoice;

    @Column(name = "bag_type")
    private String bagType;

    @Column(name = "validity_date")
    private LocalDate validityDate;

    @Column(name = "seed_score")
    private Integer seedScore;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
