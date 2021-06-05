package com.minsoo.co.tireerpserver.services.purchase.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.services.purchase.entity.Purchase;
import com.minsoo.co.tireerpserver.services.purchase.entity.PurchaseContent;
import com.minsoo.co.tireerpserver.services.purchase.repository.PurchaseContentRepository;
import com.minsoo.co.tireerpserver.services.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseContentService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseContentRepository purchaseContentRepository;

    public List<PurchaseContent> findAllByPurchaseDate(LocalDate from, LocalDate to) {
        return purchaseContentRepository.findAll();
    }

    public List<PurchaseContent> findAllByPurchaseId(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new NotFoundException("매입", purchaseId));
        return purchaseContentRepository.findAllByPurchase(purchase);
    }

    public PurchaseContent findById(Long id) {
        return purchaseContentRepository.findById(id).orElseThrow(() -> new NotFoundException("매입 항목", id));
    }
}