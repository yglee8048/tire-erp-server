package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.constant.DefaultValue;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.management.Vendor;
import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.entity.purchase.Purchase;
import com.minsoo.co.tireerpserver.entity.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.purchase.request.PurchaseContentRequest;
import com.minsoo.co.tireerpserver.model.purchase.request.PurchaseRequest;
import com.minsoo.co.tireerpserver.repository.management.VendorRepository;
import com.minsoo.co.tireerpserver.repository.management.WarehouseRepository;
import com.minsoo.co.tireerpserver.repository.pruchase.PurchaseContentRepository;
import com.minsoo.co.tireerpserver.repository.pruchase.PurchaseRepository;
import com.minsoo.co.tireerpserver.repository.stock.StockRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseContentRepository purchaseContentRepository;
    private final StockRepository stockRepository;
    private final TireRepository tireRepository;
    private final TireDotRepository tireDotRepository;
    private final VendorRepository vendorRepository;
    private final WarehouseRepository warehouseRepository;

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public Purchase findById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId).orElseThrow(() -> {
            log.error("Can not find purchase by id: {}", purchaseId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [매입]");
        });
    }

    public Purchase create(PurchaseRequest purchaseRequest) {
        Vendor vendor = vendorRepository.findById(purchaseRequest.getVendorId()).orElseThrow(() -> {
            log.error("Can not find vendor by id: {}", purchaseRequest.getVendorId());
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [매입처]");
        });
        Purchase purchase = purchaseRepository.save(Purchase.of(vendor, purchaseRequest));

        for (PurchaseContentRequest content : purchaseRequest.getContents()) {
            Tire tire = tireRepository.findById(content.getTireId()).orElseThrow(() -> {
                log.error("Can not find tire by id: {}", content.getTireId());
                return new NotFoundException(SystemMessage.NOT_FOUND + ": [타이어]");
            });
            TireDot tireDot = tireDotRepository.findByTireAndDot(tire, content.getDot())
                    .orElseGet(() -> tireDotRepository.save(TireDot.of(tire, content.getDot(), tire.getRetailPrice())));
            Warehouse warehouse = warehouseRepository.findById(content.getWarehouseId()).orElseThrow(() -> {
                log.error("Can not find warehouse by id: {}", content.getWarehouseId());
                return new NotFoundException(SystemMessage.NOT_FOUND + ": [창고]");
            });

            purchase.getPurchaseContents().add(purchaseContentRepository.save(PurchaseContent.of(purchase, tireDot, warehouse, content)));
        }
        return purchase;
    }

    public Purchase update(Long purchaseId, PurchaseRequest purchaseRequest) {
        Purchase purchase = findById(purchaseId);
        Vendor vendor = vendorRepository.findById(purchaseRequest.getVendorId()).orElseThrow(() -> {
            log.error("Can not find vendor by id: {}", purchaseRequest.getVendorId());
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [매입처]");
        });
        return purchase.update(vendor, purchaseRequest);
    }

    public Purchase confirm(Long purchaseId) {
        Purchase purchase = findById(purchaseId);
        if (purchase.isConfirmed()) {
            throw new BadRequestException(SystemMessage.ALREADY_CONFIRMED);
        }

        for (PurchaseContent purchaseContent : purchase.getPurchaseContents()) {
            String defaultNickname = DefaultValue.DEFAULT_STOCK_NICKNAME_PREFIX + purchaseContent.getWarehouse().getName();
            stockRepository.findByTireDotAndNickname(purchaseContent.getTireDot(), defaultNickname)
                    .map(stock -> stock.addQuantity(purchaseContent.getQuantity()))
                    .orElseGet(() -> stockRepository.save(Stock.of(purchaseContent.getTireDot(), defaultNickname, purchaseContent.getWarehouse(), purchaseContent.getQuantity(), false)));
        }
        return purchase;
    }

    public void deleteById(Long purchaseId) {
        Purchase purchase = findById(purchaseId);
        purchaseRepository.delete(purchase);
    }
}
