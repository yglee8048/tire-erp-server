package com.minsoo.co.tireerpserver.service.tire;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireRepository;
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
    private final TireRepository tireRepository;

    public List<TireDot> findAllByTireId(Long tireId) {
        Tire tire = tireRepository.findById(tireId).orElseThrow(() -> {
            log.error("Can not find tire by id: {}", tireId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [타이어]");
        });
        return tireDotRepository.findAllByTire(tire);
    }
}
