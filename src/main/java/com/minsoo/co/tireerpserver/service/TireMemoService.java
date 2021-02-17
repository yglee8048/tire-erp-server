package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.dot.memo.TireMemoCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.dot.memo.TireMemoUpdateRequest;
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

    public TireMemo findById(Long id) {
        return tireMemoRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public TireMemo create(Long tireId, TireMemoCreateRequest createRequest) {
        Tire tire = tireService.findById(tireId);
        return tireMemoRepository.save(TireMemo.of(createRequest, tire));
    }

    @Transactional
    public TireMemo updateTireMemo(Long tireMemoId, TireMemoUpdateRequest updateRequest) {
        TireMemo tireMemo = this.findById(tireMemoId);
        tireMemo.update(updateRequest);
        return tireMemo;
    }

    @Transactional
    public void remove(Long id) {
        tireMemoRepository.deleteById(id);
    }
}
