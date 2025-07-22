package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.LotWithdrawalRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotWithdrawalResponse;
import com.westflow.seeds_manager_api.domain.entity.LotWithdrawal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LotWithdrawalMapper {

    LotWithdrawal toDomain(LotWithdrawalRequest request);

    LotWithdrawalResponse toResponse(LotWithdrawal withdrawal);
}
