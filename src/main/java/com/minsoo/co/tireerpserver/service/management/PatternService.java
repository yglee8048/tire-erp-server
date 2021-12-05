package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.management.Brand;
import com.minsoo.co.tireerpserver.entity.management.Pattern;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.management.request.PatternRequest;
import com.minsoo.co.tireerpserver.repository.management.PatternRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PatternService {

    private final PatternRepository patternRepository;
    private final BrandService brandService;

    public List<Pattern> findAllByBrandId(Long brandId) {
        Brand brand = brandService.findById(brandId);
        return patternRepository.findAllByBrand(brand);
    }

    public Pattern findById(Long patternId) {
        return patternRepository.findById(patternId).orElseThrow(() -> {
            log.error("Can not find pattern by id : {}", patternId);
            return new NotFoundException(SystemMessage.NOT_FOUND);
        });
    }

    public Pattern create(Long brandId, PatternRequest request) {
        Brand brand = brandService.findById(brandId);
        return patternRepository.save(Pattern.of(brand, request));
    }

    public Pattern update(Long brandId, Long patternId, PatternRequest request) {
        Brand brand = brandService.findById(brandId);
        Pattern pattern = findById(patternId);
        return pattern.update(brand, request);
    }

    public void deleteById(Long patternId) {
        Pattern pattern = findById(patternId);
        patternRepository.delete(pattern);
    }
}
