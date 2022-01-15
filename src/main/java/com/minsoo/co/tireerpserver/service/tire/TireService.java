package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.entity.management.Pattern;
import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.TireInfoResponse;
import com.minsoo.co.tireerpserver.model.request.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.response.tire.TireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireResponse;
import com.minsoo.co.tireerpserver.repository.management.PatternRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TireService {

    private final TireRepository tireRepository;
    private final TireDotRepository tireDotRepository;
    private final PatternRepository patternRepository;

    public TireResponse findById(Long tireId) {
        return new TireResponse(findTireById(tireId));
    }

    public TireResponse create(TireRequest tireRequest) {
        Pattern pattern = findPatternById(tireRequest.getPatternId());
        return new TireResponse(tireRepository.save(Tire.of(pattern, tireRequest)));
    }

    public TireResponse update(Long tireId, TireRequest tireRequest) {
        Pattern pattern = findPatternById(tireRequest.getPatternId());
        Tire tire = findTireById(tireId);
        return new TireResponse(tire.update(pattern, tireRequest));
    }

    public void deleteById(Long tireId) {
        Tire tire = findTireById(tireId);
        tireRepository.delete(tire);
    }

    public List<TireGridResponse> findAllTireGrids() {
        Map<Long, List<TireDotGridResponse>> tireDotGridMap = tireDotRepository.findAllTireDotGrids()
                .stream()
                .collect(Collectors.groupingBy(tireDotGridResponse -> tireDotGridResponse.getTireDot().getTireId()));

        List<TireInfoResponse> tireInfos = tireRepository.findAllTireInfos();
        return tireInfos.stream()
                .collect(Collectors.toMap(TireInfoResponse::getTireId, tireInfoResponse -> tireInfoResponse))
                .entrySet().stream()
                .map(tireInfoEntry -> {
                    List<TireDotGridResponse> tireDotInfos = Optional.ofNullable(tireDotGridMap.get(tireInfoEntry.getKey())).orElse(Collections.emptyList());

                    return new TireGridResponse(tireInfoEntry.getValue(), tireDotInfos);
                })
                .collect(Collectors.toList());
    }

    private Tire findTireById(Long tireId) {
        return tireRepository.findById(tireId).orElseThrow(() -> new NotFoundException("타이어", tireId));
    }

    private Pattern findPatternById(Long patternId) {
        return patternRepository.findById(patternId).orElseThrow(() -> new NotFoundException("패턴", patternId));
    }
}
