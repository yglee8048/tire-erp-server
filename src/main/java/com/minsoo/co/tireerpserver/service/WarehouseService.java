package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.Warehouse;
import com.minsoo.co.tireerpserver.repository.WarehouseRepository;
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

    public List<String> findAllWarehouseNames() {
        return warehouseRepository.findAllWarehouseNames();
    }

    public Warehouse findById(Long id) {
        return warehouseRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Warehouse create(WarehouseCreateRequest createRequest) {
        if (warehouseRepository.existsByName(createRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        return warehouseRepository.save(Warehouse.of(createRequest));
    }

    @Transactional
    public Warehouse update(WarehouseUpdateRequest updateRequest) {
        Warehouse warehouse = this.findById(updateRequest.getWarehouseId());
        if (!warehouse.getName().equals(updateRequest.getName()) && warehouseRepository.existsByName(updateRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        warehouse.update(updateRequest);
        return warehouse;
    }

    @Transactional
    public void remove(Long id) {
        warehouseRepository.delete(this.findById(id));
    }
}
