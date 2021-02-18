package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.entity.Tire;
import com.minsoo.co.tireerpserver.model.entity.TireDot;
import com.minsoo.co.tireerpserver.repository.TireDotRepository;
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

    private final TireDotRepository tireDotRepository;
    private final TireService tireService;

    public List<TireDot> findAllByTireId(Long tireId) {
        return tireDotRepository.findAllByTire_Id(tireId);
    }

    public TireDot findById(Long id) {
        return tireDotRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public TireDot create(Long tireId, String dot) {
        Tire tire = tireService.findById(tireId);
        return tireDotRepository.save(TireDot.of(tire, dot));
    }
}
