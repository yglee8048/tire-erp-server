package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.tire.memo.TireMemoRequest;
import com.minsoo.co.tireerpserver.model.entity.Tire;
import com.minsoo.co.tireerpserver.model.entity.TireMemo;
import com.minsoo.co.tireerpserver.repository.TireMemoRepository;
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

    private final TireService tireService;
    private final TireMemoRepository tireMemoRepository;

    public List<TireMemo> findAllByTireId(Long tireId) {
        return tireMemoRepository.findAllByTire_Id(tireId);
    }

    public TireMemo findById(Long tireId, Long tireMemoId) {
        TireMemo tireMemo = tireMemoRepository.findById(tireMemoId).orElseThrow(NotFoundException::new);
        if (!tireMemo.getTire().getId().equals(tireId)) {
            log.error("타이어 ID가 일치하지 않음. input: {}, found: {}", tireId, tireMemo.getTire().getId());
            throw new NotFoundException();
        }
        return tireMemo;
    }

    @Transactional
    public TireMemo create(Long tireId, TireMemoRequest createRequest) {
        Tire tire = tireService.findById(tireId);
        return tireMemoRepository.save(TireMemo.of(createRequest, tire));
    }

    @Transactional
    public TireMemo updateTireMemo(Long tireId, Long tireMemoId, TireMemoRequest updateRequest) {
        TireMemo tireMemo = this.findById(tireId, tireMemoId);
        tireMemo.update(updateRequest);
        return tireMemo;
    }

    @Transactional
    public void removeById(Long tireId, Long tireMemoId) {
        TireMemo tireMemo = this.findById(tireId, tireMemoId);
        tireMemoRepository.delete(tireMemo);
    }
}
