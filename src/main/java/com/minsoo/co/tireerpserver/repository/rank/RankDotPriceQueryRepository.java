package com.minsoo.co.tireerpserver.repository.rank;

public interface RankDotPriceQueryRepository {

    Long getPriceByTireDotIdAndClientId(Long tireDotId, Long clientId);
}
