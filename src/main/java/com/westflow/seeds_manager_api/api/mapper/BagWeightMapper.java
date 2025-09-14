package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.BagWeightRequest;
import com.westflow.seeds_manager_api.api.dto.response.BagWeightResponse;
import com.westflow.seeds_manager_api.domain.entity.BagWeight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BagWeightMapper {

    BagWeight toDomain(BagWeightRequest request);

    BagWeightResponse toResponse(BagWeight bagWeight);

    @Mapping(target = "id", source = "id")
    BagWeight toDomain(BagWeightRequest request, Long id);
}
