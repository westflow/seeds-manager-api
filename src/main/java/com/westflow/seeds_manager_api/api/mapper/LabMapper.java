package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.LabRequest;
import com.westflow.seeds_manager_api.api.dto.response.LabResponse;
import com.westflow.seeds_manager_api.domain.model.Lab;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LabMapper {

    Lab toDomain(LabRequest request);

    LabResponse toResponse(Lab lab);

    @Mapping(target = "id", source = "id")
    Lab toDomain(LabRequest request, Long id);
}
