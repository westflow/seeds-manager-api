package com.westflow.seeds_manager_api.application.service;

import com.westflow.seeds_manager_api.api.dto.request.LotCreateRequest;
import com.westflow.seeds_manager_api.api.dto.response.LotResponse;
import com.westflow.seeds_manager_api.domain.entity.User;

public interface LotService {
    LotResponse register(LotCreateRequest request, User user);
}
