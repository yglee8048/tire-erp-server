package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.vendor.VendorCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.vendor.VendorUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.Vendor;
import com.minsoo.co.tireerpserver.repository.VendorRepository;
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
        return vendorRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Vendor create(VendorCreateRequest createRequest) {
        if (vendorRepository.existsByName(createRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        return vendorRepository.save(Vendor.of(createRequest));
    }

    @Transactional
    public Vendor update(VendorUpdateRequest updateRequest) {
        Vendor vendor = this.findById(updateRequest.getId());
        if (!vendor.getName().equals(updateRequest.getName()) && vendorRepository.existsByName(updateRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        vendor.update(updateRequest);
        return vendor;
    }

    @Transactional
    public void remove(Long id) {
        vendorRepository.delete(this.findById(id));
    }
}