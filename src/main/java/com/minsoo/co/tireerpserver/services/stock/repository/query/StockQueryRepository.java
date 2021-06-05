package com.minsoo.co.tireerpserver.services.stock.repository.query;

import com.minsoo.co.tireerpserver.api.v1.model.dto.stock.TireStockResponse;

import java.util.List;

public interface StockQueryRepository {

    List<TireStockResponse> findTireStocks(String size, String brandName, String patternName, String productId);
}
