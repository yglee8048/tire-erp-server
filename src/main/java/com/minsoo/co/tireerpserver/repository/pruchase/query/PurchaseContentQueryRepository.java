package com.minsoo.co.tireerpserver.repository.pruchase.query;

import com.minsoo.co.tireerpserver.model.response.purchase.PurchaseContentGridResponse;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseContentQueryRepository {

    List<PurchaseContentGridResponse> findPurchaseContentGrids(LocalDate from, LocalDate to);

    List<PurchaseContentGridResponse> findPurchaseContentGridsByPurchaseId(Long purchaseId);
}
