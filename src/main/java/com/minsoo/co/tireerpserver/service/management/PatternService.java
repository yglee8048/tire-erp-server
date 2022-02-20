package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.entity.management.Brand;
import com.minsoo.co.tireerpserver.entity.management.Pattern;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.management.PatternRequest;
import com.minsoo.co.tireerpserver.model.response.management.PatternResponse;
import com.minsoo.co.tireerpserver.repository.management.BrandRepository;
import com.minsoo.co.tireerpserver.repository.management.PatternRepository;
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
public class PatternService {

    private final PatternRepository patternRepository;
    private final BrandRepository brandRepository;

    public List<PatternResponse> findAllByBrandId(Long brandId) {
        Brand brand = findBrandById(brandId);
        return patternRepository.findAllByBrand(brand).stream()
                .map(PatternResponse::new)
                .collect(Collectors.toList());
    }

    public PatternResponse findById(Long patternId) {
        return new PatternResponse(findPatternById(patternId));
    }

    public PatternResponse create(Long brandId, PatternRequest request) {
        Brand brand = findBrandById(brandId);
        return new PatternResponse(patternRepository.save(Pattern.of(brand, request)));
    }

    public PatternResponse update(Long brandId, Long patternId, PatternRequest request) {
        Brand brand = findBrandById(brandId);
        Pattern pattern = findPatternById(patternId);
        return new PatternResponse(pattern.update(brand, request));
    }

    public void deleteById(Long patternId) {
        Pattern pattern = findPatternById(patternId);
        patternRepository.delete(pattern);
    }

    private Pattern findPatternById(Long patternId) {
        return patternRepository.findById(patternId).orElseThrow(() -> new NotFoundException("패턴", patternId));
    }

    private Brand findBrandById(Long brandId) {
        return brandRepository.findById(brandId).orElseThrow(() -> new NotFoundException("제조사", brandId));
    }
}
