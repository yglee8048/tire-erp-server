package com.minsoo.co.tireerpserver.repository.tire;

import com.minsoo.co.tireerpserver.model.response.tire.query.TireDotPriceResponse;

import java.util.List;

public interface TireDotQueryRepository {

    List<TireDotPriceResponse> findTireDotPricesByTireIdAndRankId(Long tireId, Long rankId);
}
