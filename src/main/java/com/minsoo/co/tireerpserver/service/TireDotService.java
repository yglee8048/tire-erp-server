package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotSimpleResponse;
import com.minsoo.co.tireerpserver.model.entity.Tire;
import com.minsoo.co.tireerpserver.repository.TireDotRepository;
import com.minsoo.co.tireerpserver.repository.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TireDotService {

    private final TireRepository tireRepository;
    private final TireDotRepository tireDotRepository;

    public TireDotSimpleResponse findById(Long id) {
        return TireDotSimpleResponse.of(tireDotRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public List<TireDotSimpleResponse> findAllByTireId(Long tireId) {
        Tire tire = tireRepository.findById(tireId).orElseThrow(NotFoundException::new);
        return tireDotRepository.findAllByTire(tire)
                .stream()
                .map(TireDotSimpleResponse::of)
                .collect(Collectors.toList());
    }
}
