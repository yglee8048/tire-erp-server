package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.management.WarehouseRequest;
import com.minsoo.co.tireerpserver.model.response.management.WarehouseResponse;
import com.minsoo.co.tireerpserver.repository.management.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public List<WarehouseResponse> findAll() {
        return warehouseRepository.findAll().stream()
                .map(WarehouseResponse::new)
                .collect(Collectors.toList());
    }

    public WarehouseResponse findById(Long warehouseId) {
        return new WarehouseResponse(findWarehouseById(warehouseId));
    }

    public WarehouseResponse create(WarehouseRequest request) {
        return new WarehouseResponse(warehouseRepository.save(Warehouse.of(request)));
    }

    public WarehouseResponse update(Long warehouseId, WarehouseRequest request) {
        Warehouse warehouse = findWarehouseById(warehouseId);
        return new WarehouseResponse(warehouse.update(request));
    }

    public void deleteById(Long warehouseId) {
        Warehouse warehouse = findWarehouseById(warehouseId);
        warehouseRepository.delete(warehouse);
    }

    private Warehouse findWarehouseById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId).orElseThrow(() -> new NotFoundException("창고", warehouseId));
    }
}
