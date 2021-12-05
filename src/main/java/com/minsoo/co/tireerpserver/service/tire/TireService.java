package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.management.Pattern;
import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.tire.request.TireRequest;
import com.minsoo.co.tireerpserver.repository.tire.TireRepository;
import com.minsoo.co.tireerpserver.service.management.PatternService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TireService {

    private final TireRepository tireRepository;
    private final PatternService patternService;

    public List<Tire> findAll() {
        return tireRepository.findAll();
    }

    public Tire findById(Long tireId) {
        return tireRepository.findById(tireId).orElseThrow(() -> {
            log.error("Can not find tire by id: {}", tireId);
            return new NotFoundException(SystemMessage.NOT_FOUND);
        });
    }

    public Tire create(TireRequest tireRequest) {
        Pattern pattern = patternService.findById(tireRequest.getPatternId());
        return tireRepository.save(Tire.of(pattern, tireRequest));
    }

    public Tire update(Long tireId, TireRequest tireRequest) {
        Pattern pattern = patternService.findById(tireRequest.getPatternId());
        Tire tire = findById(tireId);
        return tire.update(pattern, tireRequest);
    }

    public void deleteById(Long tireId) {
        Tire tire = findById(tireId);
        tireRepository.delete(tire);
    }
}
