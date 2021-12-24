package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.management.Vendor;
import com.minsoo.co.tireerpserver.entity.purchase.Purchase;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.purchase.PurchaseRequest;
import com.minsoo.co.tireerpserver.repository.management.VendorRepository;
import com.minsoo.co.tireerpserver.repository.pruchase.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final VendorRepository vendorRepository;
    private final PurchaseContentService purchaseContentService;

    public Purchase findById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId).orElseThrow(() -> {
            log.error("Can not find purchase by id: {}", purchaseId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [매입]");
        });
    }

    public Purchase create(PurchaseRequest purchaseRequest) {
        Vendor vendor = findVendorById(purchaseRequest.getVendorId());
        Purchase purchase = purchaseRepository.save(Purchase.of(vendor, purchaseRequest));

        purchaseContentService.modifyPurchaseContents(purchase, purchaseRequest.getContents());

        return purchase;
    }

    public Purchase update(Long purchaseId, PurchaseRequest purchaseRequest) {
        Vendor vendor = findVendorById(purchaseRequest.getVendorId());
        Purchase purchase = findById(purchaseId)
                .update(vendor, purchaseRequest);

        purchaseContentService.modifyPurchaseContents(purchase, purchaseRequest.getContents());

        return purchase;
    }

    public void deleteById(Long purchaseId) {
        Purchase purchase = findById(purchaseId);
        purchaseRepository.delete(purchase);
    }

    private Vendor findVendorById(Long vendorId) {
        return vendorRepository.findById(vendorId).orElseThrow(() -> {
            log.error("Can not find vendor by id: {}", vendorId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [매입처]");
        });
    }
}
