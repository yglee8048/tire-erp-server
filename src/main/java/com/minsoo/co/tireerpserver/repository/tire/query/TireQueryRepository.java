package com.minsoo.co.tireerpserver.repository.tire.query;

import com.minsoo.co.tireerpserver.model.TireInfoResponse;

import java.util.List;

public interface TireQueryRepository {

    List<TireInfoResponse> findAllTireInfos();
}
