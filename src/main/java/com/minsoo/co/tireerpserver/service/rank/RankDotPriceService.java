package com.minsoo.co.tireerpserver.service.rank;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.rank.Rank;
import com.minsoo.co.tireerpserver.entity.rank.RankDotPrice;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.rank.RankDotPriceRequest;
import com.minsoo.co.tireerpserver.repository.rank.RankDotPriceRepository;
import com.minsoo.co.tireerpserver.repository.rank.RankRepository;
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
public class RankDotPriceService {

    private final RankDotPriceRepository rankDotPriceRepository;
    private final RankRepository rankRepository;
    private final TireDotRepository tireDotRepository;

    public List<RankDotPrice> findAllByTireDotId(Long tireDotId) {
        TireDot tireDot = findTireDotById(tireDotId);
        return rankDotPriceRepository.findAllByTireDot(tireDot);
    }

    public RankDotPrice modify(RankDotPriceRequest rankDotPriceRequest) {
        Rank rank = findRankById(rankDotPriceRequest.getRankId());
        TireDot tireDot = findTireDotById(rankDotPriceRequest.getTireDotId());

        return rankDotPriceRepository.findByRankAndTireDot(rank, tireDot)
                .map(found -> found.update(rankDotPriceRequest.getPrice()))
                .orElseGet(() -> rankDotPriceRepository.save(RankDotPrice.of(rank, tireDot, rankDotPriceRequest.getPrice())));
    }

    private Rank findRankById(Long rankId) {
        return rankRepository.findById(rankId).orElseThrow(() -> {
            log.error("Can not find rank by id: {}", rankId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [등급]");
        });
    }

    private TireDot findTireDotById(Long tireDotId) {
        return tireDotRepository.findById(tireDotId).orElseThrow(() -> {
            log.error("Can not find tire-dot by id: {}", tireDotId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [타이어 DOT]");
        });
    }
}
