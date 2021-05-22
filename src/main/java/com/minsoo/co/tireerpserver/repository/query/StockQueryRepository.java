package com.minsoo.co.tireerpserver.repository.query;

import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;

import java.util.List;
import java.util.Optional;

public interface StockQueryRepository {

    List<TireStockResponse> findTireStocks(String size, String brandName, String patternName, String productId);
}
