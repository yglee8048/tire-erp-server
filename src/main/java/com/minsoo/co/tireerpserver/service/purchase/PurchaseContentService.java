package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.purchase.Purchase;
import com.minsoo.co.tireerpserver.entity.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.repository.pruchase.PurchaseContentRepository;
import com.minsoo.co.tireerpserver.repository.pruchase.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseContentService {

    private final PurchaseContentRepository purchaseContentRepository;
    private final PurchaseRepository purchaseRepository;

    public List<PurchaseContent> findAll() {
        return purchaseContentRepository.findAll();
    }

    public List<PurchaseContent> findByPurchase(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> {
            log.error("Can not find purchase by id: {}", purchaseId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [매입]");
        });
        return purchaseContentRepository.findAllByPurchase(purchase);
    }
}
