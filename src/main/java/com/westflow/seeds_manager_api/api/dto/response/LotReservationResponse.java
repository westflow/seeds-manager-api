package com.westflow.seeds_manager_api.api.dto.response;

import com.westflow.seeds_manager_api.domain.enums.LotStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LotReservationResponse {

    private Long id;
    private Long lotId;
    private String identification;
    private BigDecimal quantity;
    private LocalDate reservationDate;
    private LotStatus status;
    private Long clientId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
