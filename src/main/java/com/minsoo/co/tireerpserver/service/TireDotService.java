package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.entity.TireDot;
import com.minsoo.co.tireerpserver.repository.TireDotRepository;
import com.minsoo.co.tireerpserver.repository.TireRepository;
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
        return tireDotRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<TireDot> findAllByTireId(Long tireId) {
        return tireDotRepository.findAllByTire(
                tireRepository.findById(tireId).orElseThrow(NotFoundException::new));
    }
}
