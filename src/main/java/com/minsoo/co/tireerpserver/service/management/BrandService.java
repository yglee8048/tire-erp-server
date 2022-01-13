package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.entity.management.Brand;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.management.BrandRequest;
import com.minsoo.co.tireerpserver.model.response.management.BrandResponse;
import com.minsoo.co.tireerpserver.repository.management.BrandRepository;
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
public class BrandService {

    private final BrandRepository brandRepository;

    public List<BrandResponse> findAll() {
        return brandRepository.findAll().stream()
                .map(BrandResponse::new)
                .collect(Collectors.toList());
    }

    public BrandResponse findById(Long brandId) {
        return new BrandResponse(findBrandById(brandId));
    }

    public BrandResponse create(BrandRequest request) {
        return new BrandResponse(brandRepository.save(Brand.of(request)));
    }

    public BrandResponse update(Long brandId, BrandRequest request) {
        Brand brand = findBrandById(brandId);
        return new BrandResponse(brand.update(request));
    }

    public void deleteById(Long brandId) {
        Brand brand = findBrandById(brandId);
        brandRepository.delete(brand);
    }

    private Brand findBrandById(Long brandId) {
        return brandRepository.findById(brandId).orElseThrow(() -> new NotFoundException("제조사", brandId));
    }
}
