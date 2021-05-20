package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.CanNotDeleteException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.tire.pattern.PatternRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Pattern;
import com.minsoo.co.tireerpserver.repository.management.BrandRepository;
import com.minsoo.co.tireerpserver.repository.tire.PatternRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireRepository;
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

    public List<Pattern> findAll() {
        return patternRepository.findAll();
    }

    public Pattern findById(Long id) {
        return patternRepository.findById(id).orElseThrow(() -> new NotFoundException("패턴", id));
    }

    @Transactional
    public Pattern create(PatternRequest patternRequest) {
        if (patternRepository.existsByName(patternRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 패턴 이름입니다.");
        }
        Brand brand = brandRepository.findById(patternRequest.getBrandId()).orElseThrow(() -> new NotFoundException("제조사", patternRequest.getBrandId()));

        return patternRepository.save(Pattern.of(patternRequest, brand));
    }

    @Transactional
    public Pattern update(Long id, PatternRequest patternRequest) {
        Brand brand = brandRepository.findById(patternRequest.getBrandId()).orElseThrow(() -> new NotFoundException("제조사", patternRequest.getBrandId()));
        Pattern pattern = patternRepository.findById(id).orElseThrow(() -> new NotFoundException("패턴", id));
        if (!patternRequest.getName().equals(pattern.getName()) && patternRepository.existsByName(patternRequest.getName())) {
            throw new AlreadyExistException("이미 존재하는 패턴 이름입니다.");
        }

        return pattern.update(patternRequest, brand);
    }

    @Transactional
    public void removeById(Long id) {
        Pattern pattern = patternRepository.findById(id).orElseThrow(() -> new NotFoundException("패턴", id));
        if (tireRepository.existsByPattern(pattern)) {
            throw new CanNotDeleteException("연관된 타이어가 존재하여 삭제할 수 없습니다.");
        }

        patternRepository.delete(pattern);
    }
}
