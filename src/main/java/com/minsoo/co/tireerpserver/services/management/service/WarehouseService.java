package com.minsoo.co.tireerpserver.services.management.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.api.v1.model.dto.management.warehouse.WarehouseRequest;
import com.minsoo.co.tireerpserver.services.management.entity.Warehouse;
import com.minsoo.co.tireerpserver.services.management.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    public Warehouse findById(Long id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException("창고", id));
    }

    @Transactional
    public Warehouse create(WarehouseRequest createRequest) {
        if (warehouseRepository.existsByName(createRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        return warehouseRepository.save(Warehouse.of(createRequest));
    }

    @Transactional
    public Warehouse update(Long id, WarehouseRequest updateRequest) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException("창고", id));
        if (!warehouse.getName().equals(updateRequest.getName()) && warehouseRepository.existsByName(updateRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }

        warehouse.update(updateRequest);
        return warehouse;
    }

    @Transactional
    public void removeById(Long id) {
        warehouseRepository.deleteById(id);
    }
}
