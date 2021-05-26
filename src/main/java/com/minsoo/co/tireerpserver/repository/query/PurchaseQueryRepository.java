package com.minsoo.co.tireerpserver.repository.query;

import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseResponse;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseQueryRepository {

    List<PurchaseResponse> findPurchases(LocalDate from, LocalDate to);
}
