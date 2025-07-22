package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.LotCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.domain.entity.Lot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LotMapper {

    Lot toDomain(LotCreateRequest request);

    LotResponse toResponse(Lot lot);
}
