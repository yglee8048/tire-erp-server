package com.minsoo.co.tireerpserver.sale.repository.query;

import com.minsoo.co.tireerpserver.sale.model.SaleContentGridResponse;

import java.time.LocalDate;
import java.util.List;

public interface SaleContentQueryRepository {

    List<SaleContentGridResponse> findSaleContentsByTransactionDate(LocalDate from, LocalDate to);

    List<SaleContentGridResponse> findSaleContentsBySaleId(Long saleId);
}
