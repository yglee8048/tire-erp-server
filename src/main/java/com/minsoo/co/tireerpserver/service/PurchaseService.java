package com.minsoo.co.tireerpserver.service;

import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseRequest;
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

    public List<Purchase> create(PurchaseRequest createRequest) {
        Vendor vendor = vendorService.findById(createRequest.getVendorId());
        return createRequest.getContents()
                .stream()
                .map(content -> {
                    Warehouse warehouse = warehouseService.findById(content.getWarehouseId());
                    // 존재하지 않는 DOT 가 요청되었다면, DOT 를 새로 생성한다.
                    TireDot tireDot = tireDotRepository.findByTire_IdAndDot(content.getTireId(), content.getDot())
                            .orElseGet(() -> tireDotService.create(content.getTireId(), content.getDot()));
                    return purchaseRepository.save(Purchase.of(vendor, tireDot, warehouse, content, createRequest.getPurchasedDate()));
                })
                .collect(Collectors.toList());
    }
}
