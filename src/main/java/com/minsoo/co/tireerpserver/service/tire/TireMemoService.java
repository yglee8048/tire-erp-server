package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.entity.tire.TireMemo;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.tire.TireMemoRequest;
import com.minsoo.co.tireerpserver.repository.tire.TireMemoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TireMemoService {

    private final TireMemoRepository tireMemoRepository;
    private final TireService tireService;

    public List<TireMemo> findAllByTire(Long tireId) {
        Tire tire = tireService.findById(tireId);
        return tireMemoRepository.findAllByTire(tire);
    }

    public TireMemo findById(Long tireMemoId) {
        return tireMemoRepository.findById(tireMemoId).orElseThrow(() -> {
            log.error("Can not find tire memo by id: {}", tireMemoId);
            return new NotFoundException(SystemMessage.NOT_FOUND);
        });
    }

    public TireMemo create(Long tireId, TireMemoRequest tireMemoRequest) {
        Tire tire = tireService.findById(tireId);
        return tireMemoRepository.save(TireMemo.of(tire, tireMemoRequest));
    }

    public TireMemo update(Long tireId, Long tireMemoId, TireMemoRequest tireMemoRequest) {
        Tire tire = tireService.findById(tireId);
        TireMemo tireMemo = findById(tireMemoId);
        return tireMemo.update(tire, tireMemoRequest);
    }

    public void deleteById(Long tireMemoId) {
        TireMemo tireMemo = findById(tireMemoId);
        tireMemoRepository.delete(tireMemo);
    }
}
