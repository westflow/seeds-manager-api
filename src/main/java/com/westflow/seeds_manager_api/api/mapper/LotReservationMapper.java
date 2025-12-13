package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.LotReservationRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotReservationResponse;
import com.westflow.seeds_manager_api.domain.model.Client;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.LotReservation;
import com.westflow.seeds_manager_api.domain.model.User;
import com.westflow.seeds_manager_api.domain.enums.LotStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class LotReservationMapper {

    @Mapping(source = "lot.id", target = "lotId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "user.id", target = "userId")
    public abstract LotReservationResponse toResponse(LotReservation reservation);

    public LotReservation toDomain(LotReservationRequest request, User user, Lot lot, Client client) {
        return LotReservation.builder()
                .lot(lot)
                .identification(request.getIdentification())
                .quantity(request.getQuantity())
                .reservationDate(LocalDateTime.now())
                .client(client)
                .user(user)
                .status(LotStatus.RESERVED)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
