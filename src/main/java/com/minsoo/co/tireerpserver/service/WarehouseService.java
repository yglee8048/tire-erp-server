package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseRequest;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseResponse;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.Warehouse;
import com.minsoo.co.tireerpserver.repository.WarehouseRepository;
import com.minsoo.co.tireerpserver.repository.query.ManagementQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ManagementQueryRepository managementQueryRepository;

    public List<WarehouseResponse> findAll() {
        return warehouseRepository.findAll()
                .stream()
                .map(WarehouseResponse::of)
                .collect(Collectors.toList());
    }

    public List<WarehouseSimpleResponse> findAllWarehouseNames() {
        return managementQueryRepository.findAllWarehouseNames();
    }

    public WarehouseResponse findById(Long id) {
        return WarehouseResponse.of(warehouseRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Transactional
    public WarehouseResponse create(WarehouseRequest createRequest) {
        if (warehouseRepository.existsByName(createRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        return WarehouseResponse.of(warehouseRepository.save(Warehouse.of(createRequest)));
    }

    @Transactional
    public WarehouseResponse update(Long id, WarehouseRequest updateRequest) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!warehouse.getName().equals(updateRequest.getName()) && warehouseRepository.existsByName(updateRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        warehouse.update(updateRequest);
        return WarehouseResponse.of(warehouse);
    }

    @Transactional
    public void removeById(Long id) {
        warehouseRepository.deleteById(id);
    }
}
