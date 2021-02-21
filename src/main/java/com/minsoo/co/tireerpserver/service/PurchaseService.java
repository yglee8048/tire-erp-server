package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.api.error.errors.AlreadyConfirmedException;
import com.minsoo.co.tireerpserver.api.error.errors.NotFoundException;
import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseResponse;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.*;
import com.minsoo.co.tireerpserver.repository.*;
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

    private final VendorRepository vendorRepository;
    private final WarehouseRepository warehouseRepository;
    private final TireRepository tireRepository;
    private final TireDotRepository tireDotRepository;
    private final StockRepository stockRepository;
    private final PurchaseRepository purchaseRepository;

    public List<PurchaseResponse> findAll() {
        return purchaseRepository.findAllFetchAll()
                .stream()
                .map(PurchaseResponse::of)
                .collect(Collectors.toList());
    }

    public PurchaseResponse findById(Long id) {
        return PurchaseResponse.of(purchaseRepository.findOneFetchAllById(id).orElseThrow(NotFoundException::new));
    }

    /**
     * 명확하지 않은 DOT 가 입력된다.
     * 존재하지 않는 DOT 가 요청되었다면, DOT 를 새로 생성하여 맵핑한다.
     */
    @Transactional
    public List<PurchaseSimpleResponse> create(PurchaseCreateRequest createRequest) {
        Vendor vendor = vendorRepository.findById(createRequest.getVendorId()).orElseThrow(NotFoundException::new);
        return createRequest.getContents()
                .stream()
                .map(content -> {
                    Warehouse warehouse = warehouseRepository.findById(content.getWarehouseId()).orElseThrow(NotFoundException::new);
                    TireDot tireDot = findDotIfExistElseCreateByTireIdAndDot(content.getTireId(), content.getDot());
                    return PurchaseSimpleResponse.of(purchaseRepository.save(Purchase.of(vendor, tireDot, warehouse, content, createRequest.getPurchasedDate())));
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public PurchaseSimpleResponse update(Long id, PurchaseUpdateRequest updateRequest) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(NotFoundException::new);
        // 이미 확정된 매입 건은 수정할 수 없다.
        if (purchase.getStatus().equals(PurchaseStatus.CONFIRMED)) {
            throw new AlreadyConfirmedException();
        }

        // 변경이 있는 경우에는 새로 조회해온다.
        Vendor vendor = purchase.getVendor().getId().equals(updateRequest.getVendorId())
                ? purchase.getVendor() : vendorRepository.findById(updateRequest.getVendorId()).orElseThrow(NotFoundException::new);
        Warehouse warehouse = purchase.getWarehouse().getId().equals(updateRequest.getWarehouseId())
                ? purchase.getWarehouse() : warehouseRepository.findById(updateRequest.getWarehouseId()).orElseThrow(NotFoundException::new);
        TireDot tireDot = purchase.getTireDot().getTire().getId().equals(updateRequest.getTireId()) && purchase.getTireDot().getDot().equals(updateRequest.getDot())
                ? purchase.getTireDot() : findDotIfExistElseCreateByTireIdAndDot(updateRequest.getTireId(), updateRequest.getDot());

        purchase.update(vendor, tireDot, warehouse, updateRequest);
        return PurchaseSimpleResponse.of(purchase);
    }

    /**
     * 매입 내역을 재고에 반영한다.
     * 재고가 존재하지 않는다면, 재고를 새로 생성하여 반영한다.
     */
    @Transactional
    public PurchaseSimpleResponse confirm(Long id, boolean lock) {
        Purchase purchase = purchaseRepository.findOneFetchTireDotById(id).orElseThrow(NotFoundException::new);
        Stock stock = stockRepository.findOneByTireDotAndWarehouse(purchase.getTireDot(), purchase.getWarehouse())
                // 재고가 존재하지 않는다면, 재고를 새로 생성하여 반영한다.
                .orElseGet(() -> stockRepository.save(Stock.of(purchase.getTireDot(), purchase.getWarehouse(), lock)));
        // 재고 반영
        stock.addQuantity(purchase.getQuantity());
        // 매입 확정
        purchase.confirm();
        return PurchaseSimpleResponse.of(purchase);
    }

    @Transactional
    public void removeById(Long id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(NotFoundException::new);
        // 이미 확정된 매입 건은 삭제할 수 없다.
        if (purchase.getStatus().equals(PurchaseStatus.CONFIRMED)) {
            throw new AlreadyConfirmedException();
        }
        purchaseRepository.delete(purchase);
    }

    private TireDot findDotIfExistElseCreateByTireIdAndDot(Long tireId, String dot) {
        Tire tire = tireRepository.findById(tireId).orElseThrow(NotFoundException::new);
        return tireDotRepository.findByTireAndDot(tire, dot)
                // 존재하지 않는 DOT 가 요청되었다면, DOT 를 새로 생성하여 리턴한다
                .orElseGet(() -> {
                    return tireDotRepository.save(TireDot.of(tire, dot));
                });
    }
}
