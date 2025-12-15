package com.westflow.seeds_manager_api.application.support.lot;

import com.westflow.seeds_manager_api.domain.model.BagType;
import com.westflow.seeds_manager_api.domain.model.BagWeight;
import com.westflow.seeds_manager_api.domain.model.Invoice;
import com.westflow.seeds_manager_api.domain.model.Lab;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record LotContext(
        BagWeight bagWeight,
        BagType bagType,
        Lab lab,
        List<Invoice> invoices,
        Map<Long, BigDecimal> allocationMap
) {}
