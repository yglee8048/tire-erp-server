package com.minsoo.co.tireerpserver.repository.tire.query;

import com.minsoo.co.tireerpserver.model.response.tire.TireDotGridResponse;

import java.util.List;

public interface TireDotQueryRepository {

    List<TireDotGridResponse> findAllTireDotGrids();

    List<TireDotGridResponse> findTireDotGridsByTireIdAndOptionalRankId(Long tireId, Long rankId);

    List<TireDotGridResponse> findTireDotGridsByTireDotIdsIn(List<Long> tireDotIds, Long rankId);
}
