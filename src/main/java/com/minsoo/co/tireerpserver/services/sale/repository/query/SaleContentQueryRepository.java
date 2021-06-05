package com.minsoo.co.tireerpserver.services.sale.repository.query;

import com.minsoo.co.tireerpserver.api.v1.model.dto.sale.SaleContentGridResponse;

import java.time.LocalDate;
import java.util.List;

public interface SaleContentQueryRepository {

    List<SaleContentGridResponse> findSaleContents(LocalDate from, LocalDate to);
}
