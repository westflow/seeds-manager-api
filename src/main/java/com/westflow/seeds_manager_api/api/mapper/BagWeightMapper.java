package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.response.BagWeightResponse;
import com.westflow.seeds_manager_api.domain.model.BagWeight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BagWeightMapper {

    BagWeightResponse toResponse(BagWeight bagWeight);
}
