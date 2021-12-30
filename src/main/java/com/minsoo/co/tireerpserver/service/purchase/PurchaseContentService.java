package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.constant.ConstantValue;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.entity.purchase.Purchase;
import com.minsoo.co.tireerpserver.entity.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.purchase.PurchaseContentRequest;
import com.minsoo.co.tireerpserver.repository.management.WarehouseRepository;
import com.minsoo.co.tireerpserver.repository.pruchase.PurchaseContentRepository;
import com.minsoo.co.tireerpserver.repository.stock.StockRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseContentService {

    private final PurchaseContentRepository purchaseContentRepository;
    private final TireRepository tireRepository;
    private final TireDotRepository tireDotRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockRepository stockRepository;

    //TODO: purchase-contents 가 tire-dot 와 stock 기준으로 unique 해야함. -> grouping 혹은 validation 할 것
    public void modifyPurchaseContents(Purchase purchase, List<PurchaseContentRequest> purchaseContentRequests) {
        List<PurchaseContent> purchaseContents = purchaseContentRepository.findAllByPurchase(purchase);

        List<Long> removable = purchaseContents.stream()
                .map(PurchaseContent::getId)
                .collect(Collectors.toList());
        List<Long> stored = new ArrayList<>();

        // save and update
        for (PurchaseContentRequest purchaseContentRequest : purchaseContentRequests) {
            Tire tire = findTireById(purchaseContentRequest.getTireId());
            TireDot tireDot = findOrGetTireDotByTireAndDot(tire, purchaseContentRequest.getDot());
            Warehouse warehouse = findWarehouseById(purchaseContentRequest.getWarehouseId());
            Stock stock = findDefaultStock(tireDot, warehouse);

            stored.add(purchaseContentRepository.findByPurchaseAndTireDotAndStock(purchase, tireDot, stock)
                    .map(found -> found.update(purchaseContentRequest))
                    .orElseGet(() -> purchaseContentRepository.save(PurchaseContent.of(purchase, tireDot, stock, purchaseContentRequest)))
                    .getId());
        }

        // remove
        removable.removeAll(stored);
        if (!CollectionUtils.isEmpty(removable)) {
            purchaseContentRepository.deleteAllByIdIn(removable);
        }
    }

    private Tire findTireById(Long tireId) {
        return tireRepository.findById(tireId).orElseThrow(() -> {
            log.error("Can not find tire by id: {}", tireId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [타이어]");
        });
    }

    private TireDot findOrGetTireDotByTireAndDot(Tire tire, String dot) {
        return tireDotRepository.findByTireAndDot(tire, dot)
                .orElseGet(() -> tireDotRepository.save(TireDot.of(tire, dot)));
    }

    private Warehouse findWarehouseById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId).orElseThrow(() -> {
            log.error("Can not find warehouse by id: {}", warehouseId);
            return new NotFoundException(SystemMessage.NOT_FOUND + ": [창고]");
        });
    }

    private Stock findDefaultStock(TireDot tireDot, Warehouse warehouse) {
        return stockRepository.findByTireDotAndWarehouseAndNickname(tireDot, warehouse, ConstantValue.DEFAULT_STOCK_NICKNAME)
                .orElseGet(() -> stockRepository.save(Stock.of(tireDot, ConstantValue.DEFAULT_STOCK_NICKNAME, warehouse, 0L, true)));
    }
}
