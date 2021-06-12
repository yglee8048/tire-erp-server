package com.minsoo.co.tireerpserver.management.service;

import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.management.model.vendor.VendorRequest;
import com.minsoo.co.tireerpserver.management.entity.Vendor;
import com.minsoo.co.tireerpserver.management.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    public Vendor findById(Long id) {
        return vendorRepository.findById(id).orElseThrow(() -> new NotFoundException("매입처", id));
    }

    @Transactional
    public Vendor create(VendorRequest createRequest) {
        if (vendorRepository.existsByName(createRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        return vendorRepository.save(Vendor.of(createRequest));
    }

    @Transactional
    public Vendor update(Long id, VendorRequest updateRequest) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow(() -> new NotFoundException("매입처", id));
        if (!vendor.getName().equals(updateRequest.getName()) && vendorRepository.existsByName(updateRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }

        vendor.update(updateRequest);
        return vendor;
    }

    @Transactional
    public void removeById(Long id) {
        vendorRepository.deleteById(id);
    }
}