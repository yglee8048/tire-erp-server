package com.minsoo.co.tireerpserver.repository.sale.query;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.model.request.sale.SaleDateType;
import com.minsoo.co.tireerpserver.model.response.sale.SaleContentGridResponse;

import java.time.LocalDate;
import java.util.List;

public interface SaleContentQueryRepository {

    List<SaleContentGridResponse> findAllSaleContentGrids(SaleStatus saleStatus, SaleSource saleSource, SaleDateType saleDateType, LocalDate from, LocalDate to);

    List<SaleContentGridResponse> findSaleContentGridsByClientCompanyId(Long clientCompanyId, SaleStatus saleStatus, SaleSource saleSource, SaleDateType saleDateType, LocalDate from, LocalDate to);

    List<SaleContentGridResponse> findSaleContentGridsBySaleId(Long saleId);
}
