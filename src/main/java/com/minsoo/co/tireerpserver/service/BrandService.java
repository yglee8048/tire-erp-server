package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandRequest;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.Brand;
import com.minsoo.co.tireerpserver.repository.BrandRepository;
import com.minsoo.co.tireerpserver.repository.query.ManagementQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final ManagementQueryRepository managementQueryRepository;

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public List<BrandSimpleResponse> findAllBrandNames() {
        return managementQueryRepository.findAllBrandNames();
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Brand create(BrandRequest createRequest) {
        if (brandRepository.existsByName(createRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        return brandRepository.save(Brand.of(createRequest));
    }

    @Transactional
    public Brand update(Long brandId, BrandRequest updateRequest) {
        Brand brand = this.findById(brandId);
        if (!brand.getName().equals(updateRequest.getName()) && brandRepository.existsByName(updateRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        brand.update(updateRequest);
        return brand;
    }

    @Transactional
    public void removeById(Long id) {
        brandRepository.delete(this.findById(id));
    }
}
