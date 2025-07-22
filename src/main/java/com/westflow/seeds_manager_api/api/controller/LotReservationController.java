package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.LotReservationRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.api.mapper.LotReservationMapper;
import com.westflow.seeds_manager_api.application.service.LotReservationService;
import com.westflow.seeds_manager_api.domain.entity.LotReservation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class LotReservationController {
    private final LotReservationService lotReservationService;
    private final LotReservationMapper lotReservationMapper;

    public LotReservationController(LotReservationService lotReservationService,
                                    LotReservationMapper lotReservationMapper) {
        this.lotReservationService = lotReservationService;
        this.lotReservationMapper = lotReservationMapper;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD')")
    @PostMapping
    public ResponseEntity<LotReservationResponse> reserve(@Valid @RequestBody LotReservationRequest request) {

        LotReservation reservation = lotReservationMapper.toDomain(request);
        LotReservation saved = lotReservationService.reserve(reservation);
        LotReservationResponse response = lotReservationMapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
