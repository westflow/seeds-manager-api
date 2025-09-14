package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.LotReservationRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.domain.entity.LotReservation;
import com.westflow.seeds_manager_api.domain.entity.User;

public interface LotReservationService {
    LotReservationResponse reserve(LotReservationRequest request, User user);
}
