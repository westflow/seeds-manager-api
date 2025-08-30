package com.westflow.seeds_manager_api.api.dto.response;

import com.westflow.seeds_manager_api.domain.enums.LotCategory;
import com.westflow.seeds_manager_api.domain.enums.LotType;
import com.westflow.seeds_manager_api.domain.enums.SeedType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LotResponse {

    private Long id;
    private String lotNumber;
    private LotType lotType;
    private SeedType seedType;
    private LotCategory category;
    private Long bagWeightId;
    private Long bagTypeId;
    private BigDecimal quantityTotal;
    private BigDecimal balance;
    private String productionOrder;
    private String analysisBulletin;
    private LocalDate bulletinDate;
    private Integer hardSeeds;
    private Integer wildSeeds;
    private Integer otherCultivatedSpecies;
    private Integer tolerated;
    private Integer prohibited;
    private Long labId;
    private List<Long> invoiceIds;
    private LocalDate validityDate;
    private Integer seedScore;
    private BigDecimal purity;
}
