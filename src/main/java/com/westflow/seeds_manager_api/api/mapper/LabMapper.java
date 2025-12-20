package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.response.LabResponse;
import com.westflow.seeds_manager_api.domain.model.Lab;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabMapper {

    LabResponse toResponse(Lab lab);
}
