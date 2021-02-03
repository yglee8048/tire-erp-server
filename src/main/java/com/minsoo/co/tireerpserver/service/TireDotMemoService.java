package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.dot.memo.TireDotMemoCreateRequest;
import com.minsoo.co.tireerpserver.model.entity.TireDot;
import com.minsoo.co.tireerpserver.model.entity.TireDotMemo;
import com.minsoo.co.tireerpserver.repository.TireDotMemoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TireDotMemoService {

    private final TireDotService tireDotService;
    private final TireDotMemoRepository tireDotMemoRepository;

    public List<TireDotMemo> findAllByTireDotId(Long tireDotId) {
        return tireDotMemoRepository.findAllByTireDot_Id(tireDotId);
    }

    public TireDotMemo findById(Long id) {
        return tireDotMemoRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public TireDotMemo create(Long tireDotId, TireDotMemoCreateRequest createRequest) {
        TireDot tireDot = tireDotService.findById(tireDotId);
        return tireDotMemoRepository.save(TireDotMemo.of(createRequest, tireDot));
    }

    @Transactional
    public TireDotMemo updateMemo(Long id, String memo) {
        TireDotMemo tireDotMemo = this.findById(id);
        tireDotMemo.updateMemo(memo);
        return tireDotMemo;
    }

    @Transactional
    public TireDotMemo updateLock(Long id, boolean lock) {
        TireDotMemo tireDotMemo = this.findById(id);
        tireDotMemo.updateLock(lock);
        return tireDotMemo;
    }

    @Transactional
    public void remove(Long id) {
        tireDotMemoRepository.deleteById(id);
    }
}
