package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.BagTypeRequest;
import com.westflow.seeds_manager_api.api.dto.response.BagTypeResponse;
import com.westflow.seeds_manager_api.domain.entity.BagType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BagTypeMapper {

    BagType toDomain(BagTypeRequest request);

    BagTypeResponse toResponse(BagType bagType);

    @Mapping(target = "id", source = "id")
    BagType toDomain(BagTypeRequest request, Long id);
}
