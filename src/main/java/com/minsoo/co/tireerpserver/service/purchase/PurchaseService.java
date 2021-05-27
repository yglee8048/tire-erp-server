package com.minsoo.co.tireerpserver.service.purchase;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyConfirmedException;
import com.minsoo.co.tireerpserver.api.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.purchase.*;
import com.minsoo.co.tireerpserver.model.dto.stock.ModifyStock;
import com.minsoo.co.tireerpserver.model.dto.stock.ModifyStockRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.Purchase;
import com.minsoo.co.tireerpserver.model.entity.entities.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import com.minsoo.co.tireerpserver.repository.management.VendorRepository;
import com.minsoo.co.tireerpserver.repository.management.WarehouseRepository;
import com.minsoo.co.tireerpserver.repository.purchase.PurchaseContentRepository;
import com.minsoo.co.tireerpserver.repository.purchase.PurchaseRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
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
    public Purchase create(CreatePurchaseRequest createRequest) {
        Vendor vendor = vendorRepository.findById(createRequest.getVendorId()).orElseThrow(() -> new NotFoundException("매입처", createRequest.getVendorId()));
        Purchase purchase = purchaseRepository.save(Purchase.of(vendor, createRequest.getPurchaseDate()));

        createRequest.getContents()
                .stream()
                // 같은 tire-dot 에 대한 내용은 하나로 합쳐서 저장한다.
                .collect(Collectors.groupingBy(CreatePurchaseContentRequest::getTireDotId))
                .forEach((tireDotId, contentRequests) -> {
                    TireDot tireDot = tireDotRepository.findById(tireDotId)
                            .orElseThrow(() -> new NotFoundException("타이어 DOT", tireDotId));

                    int sumOfPrice = contentRequests.stream()
                            .map(CreatePurchaseContentRequest::getPrice)
                            .reduce(0, Integer::sum);
                    long sumOfQuantity = contentRequests.stream()
                            .map(CreatePurchaseContentRequest::getQuantity)
                            .reduce(0L, Long::sum);

                    purchase.getContents()
                            .add(purchaseContentRepository.save(PurchaseContent.of(purchase, tireDot, sumOfPrice, sumOfQuantity)));
                });

        return purchase;
    }

    @Transactional
    public Purchase update(Long purchaseId, UpdatePurchaseRequest updateRequest) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new NotFoundException("매입", purchaseId));
        // validation: 이미 확정된 매입 건은 수정할 수 없다.
        if (purchase.getStatus().equals(PurchaseStatus.CONFIRMED)) {
            throw new AlreadyConfirmedException();
        }
        // validation: purchase-content-id 가 중복되어서는 안 된다.
        List<Long> purchaseContentIds = purchase.getContents()
                .stream()
                .map(PurchaseContent::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (purchaseContentIds.size() != (new HashSet<>(purchaseContentIds)).size()) {
            throw new BadRequestException("매입 항목 ID가 중복 요청되었습니다.");
        }

        // purchase 수정
        Vendor vendor = vendorRepository.findById(updateRequest.getVendorId()).orElseThrow(() -> new NotFoundException("매입처", updateRequest.getVendorId()));
        purchase.update(vendor, updateRequest.getPurchaseDate());

        // purchase-contents: tire-dot 기준으로 묶어서 생각한다.
        Map<Long, List<UpdatePurchaseContentRequest>> tireDotIdMap = updateRequest.getContents()
                .stream()
                .collect(Collectors.groupingBy(UpdatePurchaseContentRequest::getTireDotId));

        tireDotIdMap.forEach((tireDotId, contentRequests) -> {
            TireDot tireDot = tireDotRepository.findById(tireDotId)
                    .orElseThrow(() -> new NotFoundException("타이어 DOT", tireDotId));

            int sumOfPrice = contentRequests.stream()
                    .map(UpdatePurchaseContentRequest::getPrice)
                    .reduce(0, Integer::sum);
            long sumOfQuantity = contentRequests.stream()
                    .map(UpdatePurchaseContentRequest::getQuantity)
                    .reduce(0L, Long::sum);

            purchase.findContentByTireDotId(tireDotId)
                    .ifPresentOrElse(
                            // if present, update
                            purchaseContent -> purchaseContent.update(tireDot, sumOfPrice, sumOfQuantity),
                            // if not, create and add
                            () -> purchase.getContents()
                                    .add(purchaseContentRepository.save(PurchaseContent.of(purchase, tireDot, sumOfPrice, sumOfQuantity))));

            // 삭제
            purchase.getContents()
                    .forEach(purchaseContent -> {
                        if (!tireDotIdMap.containsKey(purchaseContent.getTireDot().getId())) {
                            purchaseContent.removeFromPurchase();
                        }
                    });
        });

        return purchase;
    }

    /**
     * 매입 내역을 재고에 반영한다.
     * 재고가 존재하지 않는다면, 재고를 새로 생성하여 반영한다.
     */
    @Transactional
    public Purchase confirm(Long purchaseId, List<ModifyStockRequest> stockRequests) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new NotFoundException("매입", purchaseId));

        confirmRequests.forEach(confirmRequest -> {
            PurchaseContent purchaseContent = purchaseContentRepository.findById(confirmRequest.getPurchaseContentId())
                    .orElseThrow(() -> new NotFoundException("매입 항목", confirmRequest.getPurchaseContentId()));

            // validation: 개수
            Long sumOfQuantity = purchaseContent.getTireDot().getSumOfQuantity() + purchaseContent.getQuantity();
            Long sumOfRequests = confirmRequest.getStockRequests().stream().map(ModifyStockRequest::getQuantity).reduce(0L, Long::sum);
            if (!sumOfRequests.equals(sumOfQuantity)) {
                throw new BadRequestException("재고의 총 합이 일치하지 않습니다.");
            }

            // 재고 적용
            purchaseContent.getTireDot()
                    .modifyStocks(confirmRequest.getStockRequests()
                            .stream()
                            .map(modifyStockRequest -> {
                                Warehouse warehouse = warehouseRepository.findById(modifyStockRequest.getWarehouseId())
                                        .orElseThrow(() -> new NotFoundException("창고", modifyStockRequest.getWarehouseId()));
                                return ModifyStock.of(warehouse, modifyStockRequest);
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
