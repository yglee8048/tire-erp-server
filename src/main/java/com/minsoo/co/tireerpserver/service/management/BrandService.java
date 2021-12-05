package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.management.Brand;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.management.request.BrandRequest;
import com.minsoo.co.tireerpserver.repository.management.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Brand findById(Long brandId) {
        return brandRepository.findById(brandId).orElseThrow(() -> {
            log.error("Can not find brand by id : {}", brandId);
            return new NotFoundException(SystemMessage.NOT_FOUND);
        });
    }

    public Brand create(BrandRequest request) {
        return brandRepository.save(Brand.of(request));
    }

    public Brand update(Long brandId, BrandRequest request) {
        Brand brand = findById(brandId);
        return brand.update(request);
    }

    public void deleteById(Long brandId) {
        Brand brand = findById(brandId);
        brandRepository.delete(brand);
    }
}
