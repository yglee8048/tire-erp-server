package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireRepository;
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

    public TireDot findById(Long id) {
        return tireDotRepository.findById(id).orElseThrow(() -> new NotFoundException("타이어 DOT", id));
    }

    public List<TireDot> findAllByTireId(Long tireId) {
        return tireDotRepository.findAllByTire(
                tireRepository.findById(tireId).orElseThrow(() -> new NotFoundException("타이어", tireId)));
    }
}
