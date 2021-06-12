package com.minsoo.co.tireerpserver.tire.service;

import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.tire.model.TireRequest;
import com.minsoo.co.tireerpserver.management.entity.Pattern;
import com.minsoo.co.tireerpserver.tire.entity.Tire;
import com.minsoo.co.tireerpserver.tire.repository.TireRepository;
import com.minsoo.co.tireerpserver.management.repository.PatternRepository;
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
        if (tireRepository.existsByTireIdentification(tireRequest.getTireIdentification())) {
            throw new AlreadyExistException("이미 존재하는 타이어 ID 입니다.");
        }
        Pattern pattern = patternRepository.findById(tireRequest.getPatternId()).orElseThrow(() -> new NotFoundException("패턴", tireRequest.getPatternId()));

        return tireRepository.save(Tire.of(tireRequest, pattern));
    }

    @Transactional
    public Tire update(Long tireId, TireRequest tireRequest) {
        Tire tire = tireRepository.findById(tireId).orElseThrow(() -> new NotFoundException("타이어", tireId));
        Pattern pattern = patternRepository.findById(tireRequest.getPatternId()).orElseThrow(() -> new NotFoundException("패턴", tireRequest.getPatternId()));
        if (!tire.getTireIdentification().equals(tireRequest.getTireIdentification()) && tireRepository.existsByTireIdentification(tireRequest.getTireIdentification())) {
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
