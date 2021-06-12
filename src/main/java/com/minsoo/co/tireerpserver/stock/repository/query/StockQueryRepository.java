package com.minsoo.co.tireerpserver.stock.repository.query;

import com.minsoo.co.tireerpserver.stock.model.TireStockResponse;

import java.util.List;

public interface StockQueryRepository {

    List<TireStockResponse> findTireStocks(String size, String brandName, String patternName, String productId);
}
