package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyConfirmedException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.Purchase;
import com.minsoo.co.tireerpserver.model.entity.entities.stock.Stock;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Tire;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import com.minsoo.co.tireerpserver.repository.management.VendorRepository;
import com.minsoo.co.tireerpserver.repository.management.WarehouseRepository;
import com.minsoo.co.tireerpserver.repository.purchase.PurchaseRepository;
import com.minsoo.co.tireerpserver.repository.stock.StockRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireRepository;
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

    public List<Purchase> findAll() {
        return purchaseRepository.findAllFetchAll();
    }

    public Purchase findById(Long id) {
        return purchaseRepository.findOneFetchAllById(id).orElseThrow(() -> new NotFoundException("매입", id));
    }

    /**
     * 명확하지 않은 DOT 가 입력된다.
     * 존재하지 않는 DOT 가 요청되었다면, DOT 를 새로 생성하여 맵핑한다.
     */
    @Transactional
    public List<Purchase> create(PurchaseCreateRequest createRequest) {
        Vendor vendor = vendorRepository.findById(createRequest.getVendorId()).orElseThrow(() -> new NotFoundException("매입처", createRequest.getVendorId()));
        return createRequest.getContents()
                .stream()
                .filter(content -> content.getQuantity() > 0)
                .map(content -> {
                    Warehouse warehouse = warehouseRepository.findById(content.getWarehouseId()).orElseThrow(NotFoundException::new);
                    TireDot tireDot = findDotIfExistElseCreateByTireIdAndDot(content.getTireId(), content.getDot());
                    return purchaseRepository.save(Purchase.of(vendor, tireDot, warehouse, content, createRequest.getPurchaseDate()));
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long id, PurchaseUpdateRequest updateRequest) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(() -> new NotFoundException("매입", id));
        // 이미 확정된 매입 건은 수정할 수 없다.
        if (purchase.getStatus().equals(PurchaseStatus.CONFIRMED)) {
            throw new AlreadyConfirmedException();
        }

        // 변경이 있는 경우에는 새로 조회해온다.
        Vendor vendor = purchase.getVendor().getId().equals(updateRequest.getVendorId())
                ? purchase.getVendor()
                : vendorRepository.findById(updateRequest.getVendorId()).orElseThrow(() -> new NotFoundException("매입처", updateRequest.getVendorId()));
        Warehouse warehouse = purchase.getWarehouse().getId().equals(updateRequest.getWarehouseId())
                ? purchase.getWarehouse()
                : warehouseRepository.findById(updateRequest.getWarehouseId()).orElseThrow(() -> new NotFoundException("창고", updateRequest.getWarehouseId()));
        TireDot tireDot = purchase.getTireDot().getTire().getId().equals(updateRequest.getTireId()) && purchase.getTireDot().getDot().equals(updateRequest.getDot())
                ? purchase.getTireDot()
                : findDotIfExistElseCreateByTireIdAndDot(updateRequest.getTireId(), updateRequest.getDot());

        purchase.update(vendor, tireDot, warehouse, updateRequest);
    }

    /**
     * 매입 내역을 재고에 반영한다.
     * 재고가 존재하지 않는다면, 재고를 새로 생성하여 반영한다.
     */
    @Transactional
    public void confirm(Long id) {
        Purchase purchase = purchaseRepository.findOneFetchTireDotById(id).orElseThrow(() -> new NotFoundException("매입", id));
        Stock stock = stockRepository.findOneByTireDotAndWarehouse(purchase.getTireDot(), purchase.getWarehouse())
                // 재고가 존재하지 않는다면, 재고를 새로 생성하여 반영한다. (잠금 여부 = true 로 생성)
                .orElseGet(() -> stockRepository.save(Stock.of(purchase.getTireDot(), purchase.getWarehouse(), true)));

        // 재고 반영
        stock.addQuantity(purchase.getQuantity());
        // 매입 확정
        purchase.confirm();
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
        Tire tire = tireRepository.findById(tireId).orElseThrow(() -> new NotFoundException("타이어", tireId));
        return tireDotRepository.findByTireAndDot(tire, dot)
                // 존재하지 않는 DOT 가 요청되었다면, DOT 를 새로 생성하여 리턴한다
                .orElseGet(() -> tireDotRepository.save(TireDot.of(tire, dot)));
    }
}
