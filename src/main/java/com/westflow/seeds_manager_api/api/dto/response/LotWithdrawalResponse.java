package com.westflow.seeds_manager_api.api.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LotWithdrawalResponse {

    private Long id;
    private Long lotId;
    private String invoiceNumber;
    private BigDecimal quantity;
    private LocalDate withdrawalDate;
    private String state;
    private Long userId;
    private Long clientId;
    private LocalDateTime createdAt;
}
