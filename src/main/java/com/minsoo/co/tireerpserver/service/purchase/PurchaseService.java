package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.entity.management.Vendor;
import com.minsoo.co.tireerpserver.entity.purchase.Purchase;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.purchase.PurchaseRequest;
import com.minsoo.co.tireerpserver.model.response.purchase.PurchaseResponse;
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

    public PurchaseResponse findById(Long purchaseId) {
        return new PurchaseResponse(findPurchaseById(purchaseId));
    }

    public PurchaseResponse create(PurchaseRequest purchaseRequest) {
        Vendor vendor = findVendorById(purchaseRequest.getVendorId());
        Purchase purchase = purchaseRepository.save(Purchase.of(vendor, purchaseRequest));

        purchaseContentService.modifyPurchaseContents(purchase, purchaseRequest.getContents());

        return new PurchaseResponse(purchase);
    }

    public PurchaseResponse update(Long purchaseId, PurchaseRequest purchaseRequest) {
        Vendor vendor = findVendorById(purchaseRequest.getVendorId());
        Purchase purchase = findPurchaseById(purchaseId)
                .update(vendor, purchaseRequest);

        purchaseContentService.modifyPurchaseContents(purchase, purchaseRequest.getContents());

        return new PurchaseResponse(purchase);
    }

    public void deleteById(Long purchaseId) {
        Purchase purchase = findPurchaseById(purchaseId);
        purchaseRepository.delete(purchase);
    }

    private Vendor findVendorById(Long vendorId) {
        return vendorRepository.findById(vendorId).orElseThrow(() -> new NotFoundException("매입처", vendorId));
    }

    private Purchase findPurchaseById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId).orElseThrow(() -> new NotFoundException("매입", purchaseId));
    }
}
