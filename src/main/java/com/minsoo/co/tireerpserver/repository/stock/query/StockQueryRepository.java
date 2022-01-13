package com.minsoo.co.tireerpserver.repository.stock.query;

import com.minsoo.co.tireerpserver.model.response.stock.StockGridResponse;

import java.util.List;

public interface StockQueryRepository {

    List<StockGridResponse> findStockGridsByTireDotId(Long tireDotId);
}
