package com.minsoo.co.tireerpserver.services.purchase.repository.query;

import com.minsoo.co.tireerpserver.api.v1.model.dto.purchase.PurchaseResponse;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseQueryRepository {

    List<PurchaseResponse> findPurchases(LocalDate from, LocalDate to);
}
