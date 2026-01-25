package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.domain.model.LotReservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class LotReservationMapper {

    @Mapping(source = "lot.id", target = "lotId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "user.id", target = "userId")
    public abstract LotReservationResponse toResponse(LotReservation reservation);

}
