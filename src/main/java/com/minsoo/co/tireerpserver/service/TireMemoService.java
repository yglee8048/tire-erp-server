package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.tire.memo.TireMemoRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.memo.TireMemoResponse;
import com.minsoo.co.tireerpserver.model.entity.Tire;
import com.minsoo.co.tireerpserver.model.entity.TireMemo;
import com.minsoo.co.tireerpserver.repository.TireMemoRepository;
import com.minsoo.co.tireerpserver.repository.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TireMemoService {

    private final TireRepository tireRepository;
    private final TireMemoRepository tireMemoRepository;

    public List<TireMemoResponse> findAllByTireId(Long tireId) {
        return tireMemoRepository.findAllByTire_Id(tireId)
                .stream()
                .map(TireMemoResponse::of)
                .collect(Collectors.toList());
    }

    public TireMemoResponse findById(Long tireId, Long tireMemoId) {
        TireMemo tireMemo = findByIdAndValidateTireId(tireId, tireMemoId);
        return TireMemoResponse.of(tireMemo);
    }

    @Transactional
    public TireMemoResponse create(Long tireId, TireMemoRequest createRequest) {
        Tire tire = tireRepository.findById(tireId).orElseThrow(NotFoundException::new);
        return TireMemoResponse.of(tireMemoRepository.save(TireMemo.of(createRequest, tire)));
    }

    @Transactional
    public TireMemoResponse updateTireMemo(Long tireId, Long tireMemoId, TireMemoRequest updateRequest) {
        TireMemo tireMemo = findByIdAndValidateTireId(tireId, tireMemoId);
        tireMemo.update(updateRequest);
        return TireMemoResponse.of(tireMemo);
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
