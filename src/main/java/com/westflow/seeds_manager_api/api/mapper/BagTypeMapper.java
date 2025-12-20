package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.response.BagTypeResponse;
import com.westflow.seeds_manager_api.domain.model.BagType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BagTypeMapper {

    BagTypeResponse toResponse(BagType bagType);
}
