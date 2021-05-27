package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import com.minsoo.co.tireerpserver.repository.management.PatternRepository;
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
public class TireService {

    private final PatternRepository patternRepository;
    private final TireRepository tireRepository;

    public List<Tire> findAll() {
        return tireRepository.findAll();
    }

    public Tire findById(Long id) {
        return tireRepository.findById(id).orElseThrow(() -> new NotFoundException("타이어", id));
    }

    @Transactional
    public Tire create(TireRequest tireRequest) {
        if (tireRepository.existsByProductId(tireRequest.getProductId())) {
            throw new AlreadyExistException("이미 존재하는 타이어 ID 입니다.");
        }
        Pattern pattern = patternRepository.findById(tireRequest.getPatternId()).orElseThrow(() -> new NotFoundException("패턴", tireRequest.getPatternId()));

        return tireRepository.save(Tire.of(tireRequest, pattern));
    }

    @Transactional
    public Tire update(Long tireId, TireRequest tireRequest) {
        Tire tire = tireRepository.findById(tireId).orElseThrow(() -> new NotFoundException("타이어", tireId));
        Pattern pattern = patternRepository.findById(tireRequest.getPatternId()).orElseThrow(() -> new NotFoundException("패턴", tireRequest.getPatternId()));
        if (!tire.getProductId().equals(tireRequest.getProductId()) && tireRepository.existsByProductId(tireRequest.getProductId())) {
            throw new AlreadyExistException("이미 존재하는 타이어 ID 입니다.");
        }

        return tire.update(tireRequest, pattern);
    }

    //TODO: 삭제 검증
    @Transactional
    public void removeById(Long id) {
        tireRepository.deleteById(id);
    }
}
