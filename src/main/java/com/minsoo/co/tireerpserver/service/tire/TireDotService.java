package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.tire.request.TireDotRequest;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TireDotService {

    private final TireDotRepository tireDotRepository;
    private final TireService tireService;

    public List<TireDot> findAll() {
        return tireDotRepository.findAll();
    }

    public List<TireDot> findAllByTireId(Long tireId) {
        Tire tire = tireService.findById(tireId);
        return tireDotRepository.findAllByTire(tire);
    }

    public TireDot findById(Long tireDotId) {
        return tireDotRepository.findById(tireDotId).orElseThrow(() -> {
            log.error("Can not find tire dot by id: {}", tireDotId);
            return new NotFoundException(SystemMessage.NOT_FOUND);
        });
    }

    public TireDot create(Long tireId, TireDotRequest tireDotRequest) {
        Tire tire = tireService.findById(tireId);
        return tireDotRepository.save(TireDot.of(tire, tireDotRequest.getDot(), tireDotRequest.getPrice()));
    }

    public TireDot update(Long tireId, Long tireDotId, TireDotRequest tireDotRequest) {
        Tire tire = tireService.findById(tireId);
        TireDot tireDot = findById(tireDotId);
        return tireDot.update(tire, tireDotRequest.getDot(), tireDotRequest.getPrice());
    }

    public void deleteById(Long tireDotId) {
        TireDot tireDot = findById(tireDotId);
        tireDotRepository.delete(tireDot);
    }
}
