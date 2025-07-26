package com.westflow.seeds_manager_api.api.dto.response;

import com.westflow.seeds_manager_api.domain.enums.LotStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LotReservationResponse {

    private Long id;
    private Long lotId;
    private BigDecimal quantity;
    private LocalDate reservationDate;
    private LotStatus status;
    private Long clientId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
