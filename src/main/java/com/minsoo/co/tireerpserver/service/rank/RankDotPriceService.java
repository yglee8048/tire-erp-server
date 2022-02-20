package com.minsoo.co.tireerpserver.service.rank;

import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.entity.rank.Rank;
import com.minsoo.co.tireerpserver.entity.rank.RankDotPrice;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.rank.RankDotPriceRequest;
import com.minsoo.co.tireerpserver.model.response.rank.RankDotPriceGridResponse;
import com.minsoo.co.tireerpserver.repository.client.ClientCompanyRepository;
import com.minsoo.co.tireerpserver.repository.rank.RankDotPriceRepository;
import com.minsoo.co.tireerpserver.repository.rank.RankRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RankDotPriceService {

    private final RankDotPriceRepository rankDotPriceRepository;
    private final RankRepository rankRepository;
    private final TireDotRepository tireDotRepository;
    private final ClientCompanyRepository clientCompanyRepository;

    public List<RankDotPriceGridResponse> findAllByTireDotId(Long tireDotId) {
        return rankDotPriceRepository.findRankDotPricesByTireDotId(tireDotId);
    }

    public void modify(Long tireDotId, List<RankDotPriceRequest> rankDotPriceRequests) {
        TireDot tireDot = findTireDotById(tireDotId);
        modifyRankDotPrices(tireDot, rankDotPriceRequests);
    }

    private void modifyRankDotPrices(TireDot tireDot, List<RankDotPriceRequest> rankDotPriceRequests) {
        List<RankDotPrice> rankDotPrices = rankDotPriceRepository.findAllByTireDot(tireDot);

        List<Long> removable = rankDotPrices.stream()
                .map(RankDotPrice::getId)
                .collect(Collectors.toList());
        List<Long> stored = new ArrayList<>();

        // save and update
        for (RankDotPriceRequest rankDotPriceRequest : rankDotPriceRequests) {
            Rank rank = findRankById(rankDotPriceRequest.getRankId());

            stored.add(rankDotPriceRepository.findByRankAndTireDot(rank, tireDot)
                    .map(found -> found.updateDiscountPrice(rankDotPriceRequest.getDiscountRate()))
                    .orElseGet(() -> rankDotPriceRepository.save(RankDotPrice.of(rank, tireDot, rankDotPriceRequest.getDiscountRate())))
                    .getId());
        }

        removable.removeAll(stored);
        if (!CollectionUtils.isEmpty(removable)) {
            rankDotPriceRepository.deleteAllById(removable);
        }
    }

    private ClientCompany findClientCompanyById(Long clientCompanyId) {
        return clientCompanyRepository.findById(clientCompanyId).orElseThrow(() -> new NotFoundException("고객사", clientCompanyId));
    }

    private Rank findRankById(Long rankId) {
        return rankRepository.findById(rankId).orElseThrow(() -> new NotFoundException("고객사 등급", rankId));
    }

    private TireDot findTireDotById(Long tireDotId) {
        return tireDotRepository.findById(tireDotId).orElseThrow(() -> new NotFoundException("타이어 DOT", tireDotId));
    }
}
