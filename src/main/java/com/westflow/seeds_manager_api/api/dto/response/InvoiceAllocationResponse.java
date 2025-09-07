package com.westflow.seeds_manager_api.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceAllocationResponse {

    private Long invoiceId;
    private BigDecimal quantity;
}
