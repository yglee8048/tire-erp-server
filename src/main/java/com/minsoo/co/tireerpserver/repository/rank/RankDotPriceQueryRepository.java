package com.minsoo.co.tireerpserver.repository.rank;

import com.minsoo.co.tireerpserver.model.response.rank.RankDotPriceGridResponse;

import java.util.List;

public interface RankDotPriceQueryRepository {

    Long getPriceByTireDotIdAndClientId(Long tireDotId, Long clientId);

    List<RankDotPriceGridResponse> findRankDotPricesByTireDotId(Long tireDotId);
}
