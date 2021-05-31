package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.api.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.tire.memo.TireMemoRequest;
import com.minsoo.co.tireerpserver.model.entity.tire.TireMemo;
import com.minsoo.co.tireerpserver.repository.tire.TireMemoRepository;
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
public class TireMemoService {

    private final TireRepository tireRepository;
    private final TireMemoRepository tireMemoRepository;

    public List<TireMemo> findAllByTireId(Long tireId) {
        return tireMemoRepository.findAllByTire(
                tireRepository.findById(tireId).orElseThrow(NotFoundException::new));
    }

    public TireMemo findById(Long tireMemoId) {
        return tireMemoRepository.findById(tireMemoId).orElseThrow(() -> new NotFoundException("타이어 메모", tireMemoId));
    }

    @Transactional
    public TireMemo create(Long tireId, TireMemoRequest createRequest) {
        return tireMemoRepository.save(TireMemo.of(createRequest, tireRepository.findById(tireId).orElseThrow(NotFoundException::new)));
    }

    @Transactional
    public TireMemo update(Long tireId, Long tireMemoId, TireMemoRequest updateRequest) {
        TireMemo tireMemo = tireMemoRepository.findById(tireMemoId).orElseThrow(() -> new NotFoundException("타이어 메모", tireMemoId));
        if (!tireMemo.getTire().getId().equals(tireId)) {
            log.error("Tire-id is not matched. input: {}, found {}", tireId, tireMemo.getTire().getId());
            throw new BadRequestException("타이어 ID 가 일치하지 않습니다.");
        }

        return tireMemo.update(updateRequest);
    }

    @Transactional
    public void removeById(Long tireMemoId) {
        TireMemo tireMemo = tireMemoRepository.findById(tireMemoId).orElseThrow(() -> new NotFoundException("타이어 메모", tireMemoId));
        tireMemoRepository.delete(tireMemo);
    }
}
