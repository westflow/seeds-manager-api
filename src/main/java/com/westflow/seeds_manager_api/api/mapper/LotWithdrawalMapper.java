package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
import com.westflow.seeds_manager_api.domain.model.LotWithdrawal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class LotWithdrawalMapper {

    @Mapping(source = "lot.id", target = "lotId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "user.id", target = "userId")
    public abstract LotWithdrawalResponse toResponse(LotWithdrawal withdrawal);

}
