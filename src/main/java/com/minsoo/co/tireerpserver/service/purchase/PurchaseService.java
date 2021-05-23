package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyConfirmedException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.purchase.CreatePurchaseRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.UpdatePurchaseRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.Purchase;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import com.minsoo.co.tireerpserver.repository.management.VendorRepository;
import com.minsoo.co.tireerpserver.repository.purchase.PurchaseContentRepository;
import com.minsoo.co.tireerpserver.repository.purchase.PurchaseRepository;
import com.minsoo.co.tireerpserver.repository.stock.StockRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseService {

    private final VendorRepository vendorRepository;
    private final TireDotRepository tireDotRepository;
    private final StockRepository stockRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseContentRepository purchaseContentRepository;

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public Purchase findById(Long id) {
        return purchaseRepository.findById(id).orElseThrow(() -> new NotFoundException("매입", id));
    }

    @Transactional
    public Purchase create(CreatePurchaseRequest createRequest) {
        Vendor vendor = vendorRepository.findById(createRequest.getVendorId()).orElseThrow(() -> new NotFoundException("매입처", createRequest.getVendorId()));
        Purchase purchase = purchaseRepository.save(Purchase.of(vendor, createRequest.getPurchaseDate()));
        // contents
        createRequest.getContents()
                .forEach(contentCreateRequest -> {
                    TireDot tireDot = tireDotRepository.findById(contentCreateRequest.getTireDotId())
                            .orElseThrow(() -> new NotFoundException("타이어 DOT", contentCreateRequest.getTireDotId()));
                    purchase.getPurchaseContents().add(purchaseContentRepository.save(PurchaseContent.of(purchase, tireDot, contentCreateRequest)));
                });
        return purchase;
    }

    @Transactional
    public Purchase update(Long purchaseId, UpdatePurchaseRequest updateRequest) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new NotFoundException("매입", purchaseId));
        // validation: 이미 확정된 매입 건은 수정할 수 없다.
        if (purchase.getStatus().equals(PurchaseStatus.CONFIRMED)) {
            throw new AlreadyConfirmedException();
        }

        Vendor vendor = vendorRepository.findById(updateRequest.getVendorId()).orElseThrow(() -> new NotFoundException("매입처", updateRequest.getVendorId()));
        purchase.update(vendor, updateRequest.getPurchaseDate());

        // contents
        updateRequest.getContents()
                .forEach(contentUpdateRequest -> {
                    TireDot tireDot = tireDotRepository.findById(contentUpdateRequest.getTireDotId())
                            .orElseThrow(() -> new NotFoundException("타이어 DOT", contentUpdateRequest.getTireDotId()));
                    PurchaseContent purchaseContent = purchaseContentRepository.findById(contentUpdateRequest.getPurchaseContentId())
                            .orElseThrow(() -> new NotFoundException("매입 항목", contentUpdateRequest.getPurchaseContentId()));

                    purchaseContent.update(tireDot, contentUpdateRequest);
                });

        return purchase;
    }

    /**
     * 매입 내역을 재고에 반영한다.
     * 재고가 존재하지 않는다면, 재고를 새로 생성하여 반영한다.
     */
    @Transactional
    public void confirm(Long id) {
        
    }

    /**
     * 확정된 매입인 경우 재고를 다시 반영하여 삭제한다.
     */
    @Transactional
    public void removeById(Long id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(NotFoundException::new);
        // 이미 확정된 매입 건은 삭제할 수 없다.
        if (purchase.getStatus().equals(PurchaseStatus.CONFIRMED)) {
            throw new AlreadyConfirmedException();
        }
        purchaseRepository.delete(purchase);
    }
}
