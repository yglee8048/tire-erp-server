package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import com.minsoo.co.tireerpserver.repository.management.BrandRepository;
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
        return brandRepository.findById(id).orElseThrow(() -> new NotFoundException("제조사", id));
    }

    @Transactional
    public Brand create(BrandRequest createRequest) {
        // 제조사의 이름은 유니크하다.
        if (brandRepository.existsByName(createRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 제조사 이름입니다.");
        }
        return brandRepository.save(Brand.of(createRequest));
    }

    @Transactional
    public void update(Long id, BrandRequest updateRequest) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new NotFoundException("제조사", id));
        // 이름을 바꾸는 경우 이미 존재하는 이름으로 바꿀 수는 없다.
        if (!brand.getName().equals(updateRequest.getName()) && brandRepository.existsByName(updateRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 제조사 이름입니다.");
        }

        brand.update(updateRequest);
    }

    @Transactional
    public void removeById(Long id) {
        brandRepository.deleteById(id);
    }
}
