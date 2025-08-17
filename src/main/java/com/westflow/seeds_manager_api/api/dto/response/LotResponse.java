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

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LotResponse {

    private Long id;
    private String lotNumber;
    private LotType lotType;
    private Long seedId;
    private SeedType seedType;
    private LotCategory category;
    private BigDecimal bagWeight;
    private BigDecimal balance;
    private String analysisBulletin;
    private LocalDate bulletinDate;
    private Long invoiceId;
    private String bagType;
    private LocalDate validityDate;
    private Integer seedScore;
}
