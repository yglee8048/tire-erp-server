package com.minsoo.co.tireerpserver.management.service;

import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.management.model.warehouse.WarehouseRequest;
import com.minsoo.co.tireerpserver.management.entity.Warehouse;
import com.minsoo.co.tireerpserver.management.repository.WarehouseRepository;
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
        return warehouseRepository.findById(id).orElseThrow(() -> NotFoundException.of("창고"));
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
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> NotFoundException.of("창고"));
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
