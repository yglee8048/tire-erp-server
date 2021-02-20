package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotSimpleResponse;
import com.minsoo.co.tireerpserver.repository.TireDotRepository;
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

    private final TireDotRepository tireDotRepository;

    public List<TireDotSimpleResponse> findAllByTireId(Long tireId) {
        return tireDotRepository.findAllByTire_Id(tireId)
                .stream()
                .map(TireDotSimpleResponse::of)
                .collect(Collectors.toList());
    }
}
