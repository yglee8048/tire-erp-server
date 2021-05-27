package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.Purchase;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.repository.purchase.PurchaseContentRepository;
import com.minsoo.co.tireerpserver.repository.purchase.PurchaseRepository;
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

    public List<PurchaseContent> findAll(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new NotFoundException("매입", purchaseId));
        return purchaseContentRepository.findAllByPurchase(purchase);
    }

    public PurchaseContent findById(Long id) {
        return purchaseContentRepository.findById(id).orElseThrow(() -> new NotFoundException("매입 항목", id));
    }
}
