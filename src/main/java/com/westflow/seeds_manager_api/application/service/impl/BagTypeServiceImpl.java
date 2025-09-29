package com.westflow.seeds_manager_api.application.service.impl;

import com.westflow.seeds_manager_api.api.dto.request.BagTypeRequest;
import com.westflow.seeds_manager_api.api.dto.response.BagTypeResponse;
import com.westflow.seeds_manager_api.api.mapper.BagTypeMapper;
import com.westflow.seeds_manager_api.application.service.BagTypeService;
import com.westflow.seeds_manager_api.domain.entity.BagType;
import com.westflow.seeds_manager_api.domain.exception.ResourceNotFoundException;
import com.westflow.seeds_manager_api.domain.repository.BagTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BagTypeServiceImpl implements BagTypeService {

    private final BagTypeRepository bagTypeRepository;
    private final BagTypeMapper mapper;

    @Override
    public BagTypeResponse register(BagTypeRequest request) {
        BagType bagType = mapper.toDomain(request);
        BagType saved = bagTypeRepository.save(bagType);
        return mapper.toResponse(saved);
    }

    @Override
    public BagTypeResponse findById(Long id) {
        return mapper.toResponse(getBagTypeById(id));
    }

    @Override
    public BagType findEntityById(Long id) {
        return getBagTypeById(id);
    }

    @Override
    public void delete(Long id) {
        BagType bagType = getBagTypeById(id);

        bagType.deactivate();
        bagTypeRepository.save(bagType);
    }

    @Override
    public Page<BagTypeResponse> findAll(Pageable pageable) {
        return bagTypeRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public BagTypeResponse update(Long id, BagTypeRequest request) {
        BagType bagType = mapper.toDomain(request);
        BagType existing = getBagTypeById(id);

        if (!existing.isActive()) {
            throw new IllegalStateException("Tipo de sacaria está inativo e não pode ser atualizado.");
        }

        bagType = BagType.builder()
            .id(existing.getId())
            .name(bagType.getName())
            .build();
        BagType saved = bagTypeRepository.save(bagType);
        return mapper.toResponse(saved);
    }

    private BagType getBagTypeById(Long id) {
        return bagTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de sacaria", id));
    }
}
