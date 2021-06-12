package com.minsoo.co.tireerpserver.management.service;

import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.CanNotDeleteException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.management.model.pattern.PatternRequest;
import com.minsoo.co.tireerpserver.management.entity.Brand;
import com.minsoo.co.tireerpserver.management.entity.Pattern;
import com.minsoo.co.tireerpserver.management.repository.BrandRepository;
import com.minsoo.co.tireerpserver.management.repository.PatternRepository;
import com.minsoo.co.tireerpserver.tire.repository.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PatternService {

    private final BrandRepository brandRepository;
    private final PatternRepository patternRepository;
    private final TireRepository tireRepository;

    public List<Pattern> findAllByBrandId(Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new NotFoundException("제조사", brandId));
        return patternRepository.findAllByBrand(brand);
    }

    public Pattern findById(Long brandId, Long patternId) {
        Pattern pattern = patternRepository.findById(patternId).orElseThrow(() -> new NotFoundException("패턴", patternId));
        // validation: brandId 확인
        if (!pattern.getBrand().getId().equals(brandId)) {
            log.error("Brand-id is unmatched. input: {}, found: {}", brandId, pattern.getBrand().getId());
            throw new BadRequestException("브랜드 ID 가 일치하지 않습니다.");
        }
        return pattern;
    }

    @Transactional
    public Pattern create(Long brandId, PatternRequest patternRequest) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new NotFoundException("제조사", brandId));
        if (patternRepository.existsByBrandAndName(brand, patternRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 패턴 이름입니다.");
        }

        return patternRepository.save(Pattern.of(patternRequest, brand));
    }

    @Transactional
    public Pattern update(Long brandId, Long patternId, PatternRequest patternRequest) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new NotFoundException("제조사", brandId));
        Pattern pattern = patternRepository.findById(patternId).orElseThrow(() -> new NotFoundException("패턴", patternId));
        if ((!patternRequest.getName().equals(pattern.getName()) || !brandId.equals(pattern.getBrand().getId()))
                && patternRepository.existsByBrandAndName(brand, patternRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 패턴 이름입니다.");
        }

        return pattern.update(patternRequest, brand);
    }

    @Transactional
    public void removeById(Long brandId, Long patternId) {
        Pattern pattern = patternRepository.findById(patternId).orElseThrow(() -> new NotFoundException("패턴", patternId));
        // validation: brandId 확인
        if (!pattern.getBrand().getId().equals(brandId)) {
            log.error("Brand-id is unmatched. input: {}, found: {}", brandId, pattern.getBrand().getId());
            throw new BadRequestException("브랜드 ID 가 일치하지 않습니다.");
        }
        // validation: 타이어가 존재하면 삭제할 수 없다.
        if (tireRepository.existsByPattern(pattern)) {
            throw new CanNotDeleteException("연관된 타이어가 존재하여 삭제할 수 없습니다.");
        }

        patternRepository.delete(pattern);
    }
}
