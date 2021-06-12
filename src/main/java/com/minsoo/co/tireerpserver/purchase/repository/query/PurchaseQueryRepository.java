package com.minsoo.co.tireerpserver.purchase.repository.query;


import com.minsoo.co.tireerpserver.purchase.model.PurchaseResponse;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseQueryRepository {

    List<PurchaseResponse> findPurchases(LocalDate from, LocalDate to);
}
