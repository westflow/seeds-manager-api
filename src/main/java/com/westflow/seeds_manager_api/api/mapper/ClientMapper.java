package com.westflow.seeds_manager_api.api.mapper;

import com.westflow.seeds_manager_api.api.dto.request.ClientRequest;
import com.westflow.seeds_manager_api.api.dto.response.ClientResponse;
import com.westflow.seeds_manager_api.domain.entity.Client;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toDomain(ClientRequest request);

    ClientResponse toResponse(Client client);

    @Mapping(target = "id", source = "id")
    Client toDomain(ClientRequest request, long id);
}
