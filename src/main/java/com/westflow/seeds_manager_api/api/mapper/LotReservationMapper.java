package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.LotReservationRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.domain.entity.LotReservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LotReservationMapper {

    LotReservation toDomain(LotReservationRequest request);

    LotReservationResponse toResponse(LotReservation reservation);
}
