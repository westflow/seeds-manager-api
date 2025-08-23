package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.BagWeightCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.BagWeightResponse;
import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BagWeightMapper {

    BagWeight toDomain(BagWeightCreateRequest request);

    BagWeightResponse toResponse(BagWeight bagWeight);
}
