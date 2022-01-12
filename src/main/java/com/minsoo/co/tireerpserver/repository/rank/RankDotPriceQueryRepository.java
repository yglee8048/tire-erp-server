package com.minsoo.co.tireerpserver.repository.rank;

import com.minsoo.co.tireerpserver.model.response.tire.query.TireDotPriceResponse;

import java.util.List;

public interface RankDotPriceQueryRepository {

    List<TireDotPriceResponse> findTireDotPricesByTireIdAndRankId(Long tireId, Long optionalRankId);

    Long getPriceByTireDotIdAndClientId(Long tireDotId, Long clientId);
}
