package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.constant.ConstantValue;
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
import com.minsoo.co.tireerpserver.model.request.purchase.PurchaseContentRequest;
import com.minsoo.co.tireerpserver.model.request.purchase.PurchaseRequest;
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
            PurchaseContent purchaseContent = savePurchaseContent(purchase, content);
            purchase.getPurchaseContents().add(purchaseContent);
        }
        return purchase;
    }

    public Purchase update(Long purchaseId, PurchaseRequest purchaseRequest) {
        Purchase purchase = findById(purchaseId);
        Vendor vendor = vendorRepository.findById(purchaseRequest.getVendorId()).orElseThrow(() -> {
            log.error("Can not find vendor by id: {}", purchaseRequest.getVendorId());
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [매입처]");
        });
        purchase.update(vendor, purchaseRequest);

        purchase.getPurchaseContents().clear();
        for (PurchaseContentRequest content : purchaseRequest.getContents()) {
            PurchaseContent purchaseContent = savePurchaseContent(purchase, content);
            purchase.getPurchaseContents().add(purchaseContent);
        }
        return purchase;
    }

    private PurchaseContent savePurchaseContent(Purchase purchase, PurchaseContentRequest content) {
        Tire tire = tireRepository.findById(content.getTireId()).orElseThrow(() -> {
            log.error("Can not find tire by id: {}", content.getTireId());
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [타이어]");
        });
        TireDot tireDot = tireDotRepository.findByTireAndDot(tire, content.getDot())
                .orElseGet(() -> tireDotRepository.save(TireDot.of(tire, content.getDot())));
        Warehouse warehouse = warehouseRepository.findById(content.getWarehouseId()).orElseThrow(() -> {
            log.error("Can not find warehouse by id: {}", content.getWarehouseId());
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [창고]");
        });
        return purchaseContentRepository.save(PurchaseContent.of(purchase, tireDot, warehouse, content));
    }

    public Purchase confirm(Long purchaseId) {
        Purchase purchase = findById(purchaseId);
        if (purchase.isConfirmed()) {
            throw new BadRequestException(SystemMessage.ALREADY_CONFIRMED);
        }

        for (PurchaseContent purchaseContent : purchase.getPurchaseContents()) {
            stockRepository.findByTireDotAndNickname(purchaseContent.getTireDot(), ConstantValue.DEFAULT_STOCK_NICKNAME)
                    .map(stock -> stock.addQuantity(purchaseContent.getQuantity()))
                    .orElseGet(() -> stockRepository.save(
                            Stock.of(purchaseContent.getTireDot(), ConstantValue.DEFAULT_STOCK_NICKNAME, purchaseContent.getWarehouse(), purchaseContent.getQuantity().longValue(), false)));
        }
        return purchase.confirm();
    }

    public void deleteById(Long purchaseId) {
        Purchase purchase = findById(purchaseId);
        purchaseRepository.delete(purchase);
    }
}
