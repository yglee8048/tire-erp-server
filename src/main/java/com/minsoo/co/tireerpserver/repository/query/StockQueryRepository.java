package com.minsoo.co.tireerpserver.repository.query;

import com.minsoo.co.tireerpserver.model.dto.stock.TireStockParams;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;

import java.util.List;

public interface StockQueryRepository {

    List<TireStockResponse> findTireStocks(String size, String brandName, String pattern, String productId);

    List<TireStockParams> findTireStockParams();
}
