package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.tire.memo.TireMemoRequest;
import com.minsoo.co.tireerpserver.model.entity.TireMemo;
import com.minsoo.co.tireerpserver.repository.TireMemoRepository;
import com.minsoo.co.tireerpserver.repository.TireRepository;
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

    public TireMemo findById(Long tireId, Long tireMemoId) {
        return findByIdAndValidateTireId(tireId, tireMemoId);
    }

    @Transactional
    public TireMemo create(Long tireId, TireMemoRequest createRequest) {
        return tireMemoRepository.save(TireMemo.of(createRequest, tireRepository.findById(tireId).orElseThrow(NotFoundException::new)));
    }

    @Transactional
    public void updateTireMemo(Long tireId, Long tireMemoId, TireMemoRequest updateRequest) {
        TireMemo tireMemo = findByIdAndValidateTireId(tireId, tireMemoId);
        tireMemo.update(updateRequest);
    }

    @Transactional
    public void removeById(Long tireId, Long tireMemoId) {
        TireMemo tireMemo = findByIdAndValidateTireId(tireId, tireMemoId);
        tireMemoRepository.delete(tireMemo);
    }

    private TireMemo findByIdAndValidateTireId(Long tireId, Long tireMemoId) {
        TireMemo tireMemo = tireMemoRepository.findById(tireMemoId).orElseThrow(NotFoundException::new);
        if (!tireMemo.getTire().getId().equals(tireId)) {
            log.error("타이어 ID가 일치하지 않음. input: {}, found: {}", tireId, tireMemo.getTire().getId());
            throw new NotFoundException();
        }
        return tireMemo;
    }
}
