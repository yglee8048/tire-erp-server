package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorRequest;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.Vendor;
import com.minsoo.co.tireerpserver.repository.VendorRepository;
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
public class VendorService {

    private final VendorRepository vendorRepository;
    private final ManagementQueryRepository managementQueryRepository;

    public List<VendorResponse> findAll() {
        return vendorRepository.findAll()
                .stream()
                .map(VendorResponse::of)
                .collect(Collectors.toList());
    }

    public List<VendorSimpleResponse> findAllVendorNames() {
        return managementQueryRepository.findAllVendorNames();
    }

    public VendorResponse findById(Long id) {
        return VendorResponse.of(vendorRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Transactional
    public VendorResponse create(VendorRequest createRequest) {
        if (vendorRepository.existsByName(createRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        return VendorResponse.of(vendorRepository.save(Vendor.of(createRequest)));
    }

    @Transactional
    public VendorResponse update(Long id, VendorRequest updateRequest) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!vendor.getName().equals(updateRequest.getName()) && vendorRepository.existsByName(updateRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        vendor.update(updateRequest);
        return VendorResponse.of(vendor);
    }

    @Transactional
    public void removeById(Long id) {
        vendorRepository.deleteById(id);
    }
}