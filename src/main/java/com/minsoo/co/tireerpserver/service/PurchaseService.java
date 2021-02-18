package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyConfirmedException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.*;
import com.minsoo.co.tireerpserver.repository.PurchaseRepository;
import com.minsoo.co.tireerpserver.repository.StockRepository;
import com.minsoo.co.tireerpserver.repository.TireDotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final TireDotRepository tireDotRepository;
    private final StockRepository stockRepository;
    private final VendorService vendorService;
    private final WarehouseService warehouseService;
    private final TireDotService tireDotService;
    private final StockService stockService;

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public Purchase findById(Long id) {
        return purchaseRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * 명확하지 않은 DOT 가 입력된다.
     * 존재하지 않는 DOT 가 요청되었다면, DOT 를 새로 생성하여 맵핑한다.
     */
    @Transactional
    public List<Purchase> create(PurchaseCreateRequest createRequest) {
        Vendor vendor = vendorService.findById(createRequest.getVendorId());
        return createRequest.getContents()
                .stream()
                .map(content -> {
                    Warehouse warehouse = warehouseService.findById(content.getWarehouseId());
                    TireDot tireDot = findDotIfExistElseCreateByTireIdAndDot(content.getTireId(), content.getDot());
                    return purchaseRepository.save(Purchase.of(vendor, tireDot, warehouse, content, createRequest.getPurchasedDate()));
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Purchase update(Long id, PurchaseUpdateRequest updateRequest) {
        Purchase purchase = this.findById(id);
        // 이미 확정된 매입 건은 수정할 수 없다.
        if (purchase.getStatus().equals(PurchaseStatus.CONFIRMED)) {
            throw new AlreadyConfirmedException();
        }

        // 변경이 있는 경우에는 새로 조회해온다.
        Vendor vendor = purchase.getVendor().getId().equals(updateRequest.getVendorId()) ? purchase.getVendor() : vendorService.findById(updateRequest.getVendorId());
        Warehouse warehouse = purchase.getWarehouse().getId().equals(updateRequest.getWarehouseId()) ? purchase.getWarehouse() : warehouseService.findById(updateRequest.getWarehouseId());
        TireDot tireDot;
        if (purchase.getTireDot().getTire().getId().equals(updateRequest.getTireId()) && purchase.getTireDot().getDot().equals(updateRequest.getDot())) {
            tireDot = purchase.getTireDot();
        } else {
            tireDot = findDotIfExistElseCreateByTireIdAndDot(updateRequest.getTireId(), updateRequest.getDot());
        }

        purchase.update(vendor, tireDot, warehouse, updateRequest);
        return purchase;
    }

    /**
     * 매입 내역을 재고에 반영한다.
     * 재고가 존재하지 않는다면, 재고를 새로 생성하여 반영한다.
     */
    @Transactional
    public Purchase confirm(Long id, boolean lock) {
        Purchase purchase = this.findById(id);
        Stock stock = stockRepository.findOneFetchByTireDotIdAndWarehouseId(purchase.getTireDot().getId(), purchase.getWarehouse().getId())
                // 재고가 존재하지 않는다면, 재고를 새로 생성하여 반영한다.
                .orElseGet(() -> stockService.create(purchase.getTireDot(), purchase.getWarehouse(), lock));
        // 재고 반영
        stock.addQuantity(purchase.getQuantity());
        // 매입 확정
        purchase.confirm();
        return purchase;
    }

    private TireDot findDotIfExistElseCreateByTireIdAndDot(Long tireId, String dot) {
        return tireDotRepository.findByTire_IdAndDot(tireId, dot)
                // 존재하지 않는 DOT 가 요청되었다면, DOT 를 새로 생성하여 리턴한다
                .orElseGet(() -> tireDotService.create(tireId, dot));
    }

    @Transactional
    public void removeById(Long id) {
        Purchase purchase = this.findById(id);
        // 이미 확정된 매입 건은 삭제할 수 없다.
        if (purchase.getStatus().equals(PurchaseStatus.CONFIRMED)) {
            throw new AlreadyConfirmedException();
        }
        purchaseRepository.delete(purchase);
    }
}
