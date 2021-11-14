package com.minsoo.co.tireerpserver.purchase.service;

import com.minsoo.co.tireerpserver.purchase.entity.Purchase;
import com.minsoo.co.tireerpserver.purchase.entity.PurchaseContent;
import com.minsoo.co.tireerpserver.purchase.repository.PurchaseContentRepository;
import com.minsoo.co.tireerpserver.purchase.repository.PurchaseRepository;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseContentService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseContentRepository purchaseContentRepository;

    public List<PurchaseContent> findAllByPurchaseId(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> NotFoundException.of("매입"));
        return purchaseContentRepository.findAllByPurchase(purchase);
    }

    public PurchaseContent findById(Long id) {
        return purchaseContentRepository.findById(id).orElseThrow(() -> NotFoundException.of("매입 항목"));
    }
}
