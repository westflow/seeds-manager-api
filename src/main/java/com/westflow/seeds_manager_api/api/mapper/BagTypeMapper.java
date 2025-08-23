package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.BagTypeCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.BagTypeResponse;
import com.westflow.seeds_manager_api.domain.entity.BagType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BagTypeMapper {

    BagType toDomain(BagTypeCreateRequest request);

    BagTypeResponse toResponse(BagType bagType);
}
