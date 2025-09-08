package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.LotWithdrawalRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
import com.westflow.seeds_manager_api.domain.entity.Client;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import com.westflow.seeds_manager_api.domain.entity.LotWithdrawal;
import com.westflow.seeds_manager_api.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class LotWithdrawalMapper {

    @Mapping(source = "lot.id", target = "lotId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "user.id", target = "userId")
    public abstract LotWithdrawalResponse toResponse(LotWithdrawal withdrawal);

    public LotWithdrawal toDomain(LotWithdrawalRequest request, User user, Lot lot, Client client) {;
        return LotWithdrawal.builder()
                .lot(lot)
                .invoiceNumber(request.getInvoiceNumber())
                .quantity(request.getQuantity())
                .withdrawalDate(request.getWithdrawalDate())
                .state(request.getState())
                .client(client)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
