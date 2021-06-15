package com.minsoo.co.tireerpserver.tire.service;

import com.minsoo.co.tireerpserver.shared.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.tire.entity.Tire;
import com.minsoo.co.tireerpserver.tire.model.memo.TireMemoRequest;
import com.minsoo.co.tireerpserver.tire.entity.TireMemo;
import com.minsoo.co.tireerpserver.tire.repository.TireMemoRepository;
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
public class TireMemoService {

    private final TireRepository tireRepository;
    private final TireMemoRepository tireMemoRepository;

    public List<TireMemo> findAllByTireId(Long tireId) {
        Tire tire = tireRepository.findById(tireId).orElseThrow(() -> NotFoundException.of("타이어"));
        return tireMemoRepository.findAllByTire(tire);
    }

    public TireMemo findById(Long tireMemoId) {
        return tireMemoRepository.findById(tireMemoId).orElseThrow(() -> NotFoundException.of("타이어 메모"));
    }

    @Transactional
    public TireMemo create(Long tireId, TireMemoRequest createRequest) {
        Tire tire = tireRepository.findById(tireId).orElseThrow(() -> NotFoundException.of("타이어"));
        return tireMemoRepository.save(TireMemo.of(createRequest, tire));
    }

    @Transactional
    public TireMemo update(Long tireId, Long tireMemoId, TireMemoRequest updateRequest) {
        TireMemo tireMemo = tireMemoRepository.findById(tireMemoId).orElseThrow(() -> NotFoundException.of("타이어 메모"));
        if (!tireMemo.getTire().getId().equals(tireId)) {
            log.error("Tire-id is not matched. input: {}, found {}", tireId, tireMemo.getTire().getId());
            throw new BadRequestException("타이어 ID 가 일치하지 않습니다.");
        }

        return tireMemo.update(updateRequest);
    }

    @Transactional
    public void removeById(Long tireMemoId) {
        TireMemo tireMemo = tireMemoRepository.findById(tireMemoId).orElseThrow(() -> NotFoundException.of("타이어 메모"));
        tireMemoRepository.delete(tireMemo);
    }
}
