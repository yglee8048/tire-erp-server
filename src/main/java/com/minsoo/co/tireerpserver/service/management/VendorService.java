package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.management.Vendor;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.management.request.VendorRequest;
import com.minsoo.co.tireerpserver.repository.management.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    public Vendor findById(Long vendorId) {
        return vendorRepository.findById(vendorId).orElseThrow(() -> {
            log.error("Can not find vendor by id :{}", vendorId);
            return new NotFoundException(SystemMessage.NOT_FOUND);
        });
    }

    public Vendor create(VendorRequest request) {
        return vendorRepository.save(Vendor.of(request));
    }

    public Vendor update(Long vendorId, VendorRequest request) {
        Vendor vendor = findById(vendorId);
        return vendor.update(request);
    }

    public void deleteById(Long vendorId) {
        Vendor vendor = findById(vendorId);
        vendorRepository.delete(vendor);
    }
}