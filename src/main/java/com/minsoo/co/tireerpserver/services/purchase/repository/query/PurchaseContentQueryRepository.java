package com.minsoo.co.tireerpserver.services.purchase.repository.query;

import com.minsoo.co.tireerpserver.api.v1.model.dto.purchase.content.PurchaseContentGridResponse;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseContentQueryRepository {

    List<PurchaseContentGridResponse> findPurchaseContents(LocalDate from, LocalDate to);
}
