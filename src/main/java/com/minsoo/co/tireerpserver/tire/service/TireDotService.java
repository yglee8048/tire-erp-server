package com.minsoo.co.tireerpserver.tire.service;

import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.tire.model.dot.TireDotRequest;
import com.minsoo.co.tireerpserver.tire.entity.Tire;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import com.minsoo.co.tireerpserver.tire.repository.TireDotRepository;
import com.minsoo.co.tireerpserver.tire.repository.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TireDotService {

    private final TireRepository tireRepository;
    private final TireDotRepository tireDotRepository;

    public List<TireDot> findAllByTireId(Long tireId) {
        return tireDotRepository.findAllByTire(
                tireRepository.findById(tireId).orElseThrow(() -> new NotFoundException("타이어", tireId)));
    }

    public TireDot findById(Long tireDotId) {
        return tireDotRepository.findById(tireDotId).orElseThrow(() -> new NotFoundException("타이어 DOT", tireDotId));
    }

    @Transactional
    public TireDot create(Long tireId, TireDotRequest tireDotRequest) {
        Tire tire = tireRepository.findById(tireId).orElseThrow(() -> new NotFoundException("타이어", tireId));
        if (tireDotRepository.existsByTireAndDot(tire, tireDotRequest.getDot())) {
            throw new AlreadyExistException("이미 존재하는 DOT 입니다.");
        }

        return tireDotRepository.save(TireDot.of(tire, tireDotRequest));
    }

    @Transactional
    public TireDot update(Long tireId, Long tireDotId, TireDotRequest tireDotRequest) {
        Tire tire = tireRepository.findById(tireId).orElseThrow(() -> new NotFoundException("타이어", tireId));
        TireDot tireDot = tireDotRepository.findById(tireDotId).orElseThrow(() -> new NotFoundException("타이어 DOT", tireDotId));
        if (!tireDot.getTire().getId().equals(tire.getId())) {
            throw new BadRequestException("tire_id 가 일치하지 않습니다.");
        }
        if (!tireDot.getDot().equals(tireDotRequest.getDot()) && tireDotRepository.existsByTireAndDot(tire, tireDotRequest.getDot())) {
            throw new AlreadyExistException("이미 존재하는 DOT 입니다.");
        }

        return tireDot.update(tire, tireDotRequest);
    }

    //TODO: 삭제 검증
    @Transactional
    public void removeById(Long tireDotId) {
        tireDotRepository.deleteById(tireDotId);
    }
}
