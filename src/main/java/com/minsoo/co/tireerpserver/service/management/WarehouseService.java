package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.management.request.WarehouseRequest;
import com.minsoo.co.tireerpserver.repository.management.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    public Warehouse findById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId).orElseThrow(() -> {
            log.error("Can not find warehouse by id : {}", warehouseId);
            return new NotFoundException(SystemMessage.NOT_FOUND);
        });
    }

    public Warehouse create(WarehouseRequest request) {
        return warehouseRepository.save(Warehouse.of(request));
    }

    public Warehouse update(Long warehouseId, WarehouseRequest request) {
        Warehouse warehouse = findById(warehouseId);
        return warehouse.update(request);
    }

    public void deleteById(Long warehouseId) {
        Warehouse warehouse = findById(warehouseId);
        warehouseRepository.delete(warehouse);
    }
}
