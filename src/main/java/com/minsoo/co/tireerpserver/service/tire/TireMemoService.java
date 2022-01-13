package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.entity.tire.TireMemo;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.tire.TireMemoRequest;
import com.minsoo.co.tireerpserver.model.response.tire.TireMemoResponse;
import com.minsoo.co.tireerpserver.repository.tire.TireMemoRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TireMemoService {

    private final TireMemoRepository tireMemoRepository;
    private final TireRepository tireRepository;

    public List<TireMemoResponse> findAllByTire(Long tireId) {
        Tire tire = findTireById(tireId);
        return tireMemoRepository.findAllByTire(tire).stream()
                .map(TireMemoResponse::new)
                .collect(Collectors.toList());
    }

    public TireMemoResponse create(Long tireId, TireMemoRequest tireMemoRequest) {
        Tire tire = findTireById(tireId);
        return new TireMemoResponse(tireMemoRepository.save(TireMemo.of(tire, tireMemoRequest)));
    }

    public TireMemoResponse update(Long tireId, Long tireMemoId, TireMemoRequest tireMemoRequest) {
        Tire tire = findTireById(tireId);
        TireMemo tireMemo = findTireMemoById(tireMemoId);
        return new TireMemoResponse(tireMemo.update(tire, tireMemoRequest));
    }

    public void deleteById(Long tireMemoId) {
        TireMemo tireMemo = findTireMemoById(tireMemoId);
        tireMemoRepository.delete(tireMemo);
    }

    private Tire findTireById(Long tireId) {
        return tireRepository.findById(tireId).orElseThrow(() -> new NotFoundException("타이어", tireId));
    }

    private TireMemo findTireMemoById(Long tireMemoId) {
        return tireMemoRepository.findById(tireMemoId).orElseThrow(() -> new NotFoundException("타이어 메모", tireMemoId));
    }
}
