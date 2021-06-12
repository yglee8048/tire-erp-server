package com.minsoo.co.tireerpserver.purchase.repository.query;


import com.minsoo.co.tireerpserver.purchase.model.content.PurchaseContentGridResponse;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseContentQueryRepository {

    List<PurchaseContentGridResponse> findPurchaseContents(LocalDate from, LocalDate to);
}
