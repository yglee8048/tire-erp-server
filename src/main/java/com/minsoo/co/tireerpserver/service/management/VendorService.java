package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.entity.management.Vendor;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.management.VendorRequest;
import com.minsoo.co.tireerpserver.model.response.management.VendorResponse;
import com.minsoo.co.tireerpserver.repository.management.VendorRepository;
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
public class VendorService {

    private final VendorRepository vendorRepository;

    public List<VendorResponse> findAll() {
        return vendorRepository.findAll().stream()
                .map(VendorResponse::new)
                .collect(Collectors.toList());
    }

    public VendorResponse findById(Long vendorId) {
        return new VendorResponse(findVendorById(vendorId));
    }

    public VendorResponse create(VendorRequest request) {
        return new VendorResponse(vendorRepository.save(Vendor.of(request)));
    }

    public VendorResponse update(Long vendorId, VendorRequest request) {
        Vendor vendor = findVendorById(vendorId);
        return new VendorResponse(vendor.update(request));
    }

    public void deleteById(Long vendorId) {
        Vendor vendor = findVendorById(vendorId);
        vendorRepository.delete(vendor);
    }

    private Vendor findVendorById(Long vendorId) {
        return vendorRepository.findById(vendorId).orElseThrow(() -> new NotFoundException("매입처", vendorId));
    }
}