package com.minsoo.co.tireerpserver.repository.query;

import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;

import java.util.List;
import java.util.Optional;

public interface StockQueryRepository {

    List<TireStockResponse> findTireStocksByParams(String size, String brandName, String pattern, String productId);

    Optional<TireStockResponse> findTireStocksByTireId(Long tireId);
}
