package com.minsoo.co.tireerpserver.purchase.service;

import com.minsoo.co.tireerpserver.management.entity.Vendor;
import com.minsoo.co.tireerpserver.management.repository.VendorRepository;
import com.minsoo.co.tireerpserver.management.repository.WarehouseRepository;
import com.minsoo.co.tireerpserver.purchase.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.purchase.entity.Purchase;
import com.minsoo.co.tireerpserver.purchase.model.DefaultStockName;
import com.minsoo.co.tireerpserver.purchase.model.PurchaseRequest;
import com.minsoo.co.tireerpserver.purchase.model.PurchaseResponse;
import com.minsoo.co.tireerpserver.purchase.repository.PurchaseContentRepository;
import com.minsoo.co.tireerpserver.purchase.repository.PurchaseRepository;
import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyConfirmedException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.stock.entity.Stock;
import com.minsoo.co.tireerpserver.stock.repository.StockRepository;
import com.minsoo.co.tireerpserver.tire.entity.TireDot;
import com.minsoo.co.tireerpserver.tire.repository.TireDotRepository;
import com.minsoo.co.tireerpserver.tire.repository.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseService {

    private final VendorRepository vendorRepository;
    private final WarehouseRepository warehouseRepository;
    private final TireRepository tireRepository;
    private final TireDotRepository tireDotRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseContentRepository purchaseContentRepository;
    private final StockRepository stockRepository;

    public List<PurchaseResponse> findAll(LocalDate from, LocalDate to) {
        return purchaseRepository.findPurchases(from, to);
    }

    public Purchase findById(Long id) {
        return purchaseRepository.findById(id).orElseThrow(() -> NotFoundException.of("매입"));
    }

    @Transactional
    public Purchase create(PurchaseRequest purchaseRequest) {
        Vendor vendor = vendorRepository.findById(purchaseRequest.getVendorId()).orElseThrow(() -> NotFoundException.of("매입처"));

        return purchaseRepository.save(Purchase.of(vendor, purchaseRequest.getTransactionDate()));
    }

    @Transactional
    public Purchase update(Long purchaseId, PurchaseRequest purchaseRequest) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> NotFoundException.of("매입"));
        Vendor vendor = vendorRepository.findById(purchaseRequest.getVendorId()).orElseThrow(() -> NotFoundException.of("매입처"));
        // validation: 이미 확정된 매입 건은 수정할 수 없다.
        if (purchase.getStatus().equals(PurchaseStatus.CONFIRMED)) {
            throw new AlreadyConfirmedException();
        }

        purchase.update(vendor, purchaseRequest.getTransactionDate());
        return purchase;
    }

    /**
     * 매입 내역을 재고에 반영한다.
     * 재고가 존재하지 않는다면, 재고를 새로 생성하여 반영한다.
     */
    @Transactional
    public Purchase confirm(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> NotFoundException.of("매입"));
        if (!purchase.getStatus().equals(PurchaseStatus.REQUESTED)) {
            throw new BadRequestException(String.format("이미 %s 상태인 매입은 확정할 수 없습니다.", purchase.getStatus().getDescription()));
        }

        purchase.getContents().forEach(purchaseContent -> {
            TireDot tireDot = purchaseContent.getTireDot();

            // modify stock
            stockRepository.findByTireDotAndNickname(tireDot, DefaultStockName.DEFAULT.name())
                    .ifPresentOrElse(
                            stock -> stock.addQuantity(purchaseContent.getQuantity()),
                            () -> stockRepository.save(Stock.of(tireDot, purchaseContent.getWarehouse(), DefaultStockName.DEFAULT.name(), purchaseContent.getQuantity(), false)));
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
