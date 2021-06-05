package com.minsoo.co.tireerpserver.services.purchase.service;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyConfirmedException;
import com.minsoo.co.tireerpserver.api.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.api.v1.model.dto.purchase.PurchaseRequest;
import com.minsoo.co.tireerpserver.api.v1.model.dto.purchase.PurchaseResponse;
import com.minsoo.co.tireerpserver.services.purchase.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.api.v1.model.dto.purchase.content.PurchaseContentConfirmRequest;
import com.minsoo.co.tireerpserver.api.v1.model.dto.purchase.content.PurchaseContentRequest;
import com.minsoo.co.tireerpserver.api.v1.model.dto.stock.ModifyStock;
import com.minsoo.co.tireerpserver.services.management.entity.Vendor;
import com.minsoo.co.tireerpserver.services.management.entity.Warehouse;
import com.minsoo.co.tireerpserver.services.purchase.entity.Purchase;
import com.minsoo.co.tireerpserver.services.purchase.entity.PurchaseContent;
import com.minsoo.co.tireerpserver.services.management.repository.VendorRepository;
import com.minsoo.co.tireerpserver.services.management.repository.WarehouseRepository;
import com.minsoo.co.tireerpserver.services.purchase.repository.PurchaseRepository;
import com.minsoo.co.tireerpserver.services.tire.entity.TireDot;
import com.minsoo.co.tireerpserver.services.tire.repository.TireDotRepository;
import com.minsoo.co.tireerpserver.services.purchase.repository.PurchaseContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseService {

    private final VendorRepository vendorRepository;
    private final WarehouseRepository warehouseRepository;
    private final TireDotRepository tireDotRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseContentRepository purchaseContentRepository;

    public List<PurchaseResponse> findAll(LocalDate from, LocalDate to) {
        return purchaseRepository.findPurchases(from, to);
    }

    public Purchase findById(Long id) {
        return purchaseRepository.findById(id).orElseThrow(() -> new NotFoundException("매입", id));
    }

    @Transactional
    public Purchase create(PurchaseRequest purchaseRequest) {
        Vendor vendor = vendorRepository.findById(purchaseRequest.getVendorId()).orElseThrow(() -> new NotFoundException("매입처", purchaseRequest.getVendorId()));

        return purchaseRepository.save(Purchase.of(vendor, purchaseRequest.getPurchaseDate(), makeContentMap(purchaseRequest)));
    }

    @Transactional
    public Purchase update(Long purchaseId, PurchaseRequest purchaseRequest) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new NotFoundException("매입", purchaseId));
        Vendor vendor = vendorRepository.findById(purchaseRequest.getVendorId()).orElseThrow(() -> new NotFoundException("매입처", purchaseRequest.getVendorId()));
        // validation: 이미 확정된 매입 건은 수정할 수 없다.
        if (purchase.getStatus().equals(PurchaseStatus.CONFIRMED)) {
            throw new AlreadyConfirmedException();
        }

        purchase.update(vendor, purchaseRequest.getPurchaseDate(), makeContentMap(purchaseRequest));

        return purchase;
    }

    private Map<TireDot, List<PurchaseContentRequest>> makeContentMap(PurchaseRequest purchaseRequest) {
        return purchaseRequest.getContents()
                .stream()
                .collect(Collectors.groupingBy(
                        contentRequest -> tireDotRepository.findById(contentRequest.getTireDotId())
                                .orElseThrow(() -> new NotFoundException("타이어 DOT", contentRequest.getTireDotId()))));
    }

    /**
     * 매입 내역을 재고에 반영한다.
     * 재고가 존재하지 않는다면, 재고를 새로 생성하여 반영한다.
     */
    @Transactional
    public Purchase confirm(Long purchaseId, List<PurchaseContentConfirmRequest> contentConfirmRequests) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new NotFoundException("매입", purchaseId));
        if (purchase.getContents().size() != contentConfirmRequests.size()) {
            throw new BadRequestException("매입 항목이 모두 확정되어야 합니다.");
        }

        contentConfirmRequests.forEach(purchaseContentConfirmRequest -> {
            PurchaseContent purchaseContent = purchaseContentRepository.findById(purchaseContentConfirmRequest.getPurchaseContentId())
                    .orElseThrow(() -> new NotFoundException("매입 항목", purchaseContentConfirmRequest.getPurchaseContentId()));
            if (!purchaseContent.getPurchase().getId().equals(purchaseId)) {
                throw new BadRequestException("purchase_id 에 포함되지 않는 purchase_content_id 입니다.");
            }
            TireDot tireDot = purchaseContent.getTireDot();

            // validate: 개수의 합이 같아야 한다.
            if (!tireDot.isValidPurchaseQuantity(purchaseContentConfirmRequest.getStockRequests(), purchaseContent.getQuantity())) {
                throw new BadRequestException("재고의 총 합이 일치하지 않습니다.");
            }

            // modify stock
            tireDot.modifyStocks(purchaseContentConfirmRequest.getStockRequests()
                    .stream()
                    .map(stockRequest -> {
                        Warehouse warehouse = warehouseRepository.findById(stockRequest.getWarehouseId())
                                .orElseThrow(() -> new NotFoundException("창고", stockRequest.getWarehouseId()));
                        return ModifyStock.of(warehouse, stockRequest);
                    })
                    .collect(Collectors.toList()));
        });

        return purchase.updateStatus(PurchaseStatus.CONFIRMED);
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
