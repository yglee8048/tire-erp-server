package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.Brand;
import com.minsoo.co.tireerpserver.repository.BrandRepository;
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

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Brand create(BrandCreateRequest createRequest) {
        if (brandRepository.existsByName(createRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        return brandRepository.save(Brand.of(createRequest));
    }

    @Transactional
    public Brand update(BrandUpdateRequest updateRequest) {
        Brand brand = this.findById(updateRequest.getId());
        if (!brand.getName().equals(updateRequest.getName()) && brandRepository.existsByName(updateRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 이름입니다.");
        }
        brand.update(updateRequest);
        return brand;
    }

    @Transactional
    public void remove(Long id) {
        brandRepository.delete(this.findById(id));
    }
}
