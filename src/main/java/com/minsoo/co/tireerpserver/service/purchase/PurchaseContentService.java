package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.constant.ConstantValue;
import com.minsoo.co.tireerpserver.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.entity.purchase.Purchase;
import com.minsoo.co.tireerpserver.entity.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.Tire;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.exception.NotFoundException;
import com.minsoo.co.tireerpserver.model.request.purchase.PurchaseContentRequest;
import com.minsoo.co.tireerpserver.model.response.purchase.PurchaseContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireDotGridResponse;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<PurchaseContentGridResponse> findAllPurchaseContentGrids(LocalDate from, LocalDate to) {
        List<PurchaseContentGridResponse> purchaseContentGrids = purchaseContentRepository.findPurchaseContentGrids(from, to);
        return setTireDotGridToPurchaseContentGrids(purchaseContentGrids);
    }

    public List<PurchaseContentGridResponse> findPurchaseContentGridsByPurchaseId(Long purchaseId) {
        List<PurchaseContentGridResponse> purchaseContentGrids = purchaseContentRepository.findPurchaseContentGridsByPurchaseId(purchaseId);
        return setTireDotGridToPurchaseContentGrids(purchaseContentGrids);
    }

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
        return tireRepository.findById(tireId).orElseThrow(() -> new NotFoundException("타이어", tireId));
    }

    private TireDot findOrGetTireDotByTireAndDot(Tire tire, String dot) {
        return tireDotRepository.findByTireAndDot(tire, dot)
                .orElseGet(() -> tireDotRepository.save(TireDot.of(tire, dot)));
    }

    private Warehouse findWarehouseById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId).orElseThrow(() -> new NotFoundException("창고", warehouseId));
    }

    private Stock findDefaultStock(TireDot tireDot, Warehouse warehouse) {
        return stockRepository.findByTireDotAndWarehouseAndNickname(tireDot, warehouse, ConstantValue.DEFAULT_STOCK_NICKNAME)
                .orElseGet(() -> stockRepository.save(Stock.of(tireDot, ConstantValue.DEFAULT_STOCK_NICKNAME, warehouse, 0, true)));
    }

    private List<PurchaseContentGridResponse> setTireDotGridToPurchaseContentGrids(List<PurchaseContentGridResponse> purchaseContentGridResponses) {
        List<Long> tireDotIds = purchaseContentGridResponses.stream()
                .map(PurchaseContentGridResponse::getTireDotId)
                .collect(Collectors.toList());
        Map<Long, TireDotGridResponse> tireDotGridResponseMap = getTireDotGridMap(tireDotIds);

        return purchaseContentGridResponses.stream()
                .map(purchaseContentGridResponse -> purchaseContentGridResponse.setTireDotGrid(tireDotGridResponseMap.get(purchaseContentGridResponse.getTireDotId())))
                .collect(Collectors.toList());
    }

    private Map<Long, TireDotGridResponse> getTireDotGridMap(List<Long> tireDotIds) {
        return tireDotRepository.findTireDotGridsByTireDotIdsIn(tireDotIds, null)
                .stream()
                .collect(Collectors.toMap(tireDotGrid -> tireDotGrid.getTireDot().getTireDotId(), tireDotGrid -> tireDotGrid));
    }
}
