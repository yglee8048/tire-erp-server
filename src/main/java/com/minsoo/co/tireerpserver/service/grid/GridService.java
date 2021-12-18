package com.minsoo.co.tireerpserver.service.grid;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.model.TireStandardDTO;
import com.minsoo.co.tireerpserver.model.request.sale.SaleDateType;
import com.minsoo.co.tireerpserver.model.response.grid.PurchaseContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.TireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.TireGridResponse;
import com.minsoo.co.tireerpserver.repository.grid.GridRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GridService {

    private final GridRepository gridRepository;

    public List<TireGridResponse> findAllTireGrids() {
        Map<Long, TireStandardDTO> tireStandardDTOMap = gridRepository.findAllTireStandardDTOs().stream()
                .collect(Collectors.toMap(TireStandardDTO::getTireId, tireStandardDTO -> tireStandardDTO));

        return gridRepository.findAllTireDotGrids().stream()
                .collect(Collectors.groupingBy(TireDotGridResponse::getTireId))
                .entrySet().stream()
                .map(tireDotGridMap -> {
                    Stream<TireDotGridResponse> stream = tireDotGridMap.getValue().stream();
                    return TireGridResponse.builder()
                            .tireInfo(tireStandardDTOMap.get(tireDotGridMap.getKey()))
                            .sumOfOpenedStock(stream.mapToLong(TireDotGridResponse::getSumOfOpenedStock).sum())
                            .sumOfStock(stream.mapToLong(TireDotGridResponse::getSumOfStock).sum())
                            .averageOfPurchasePrice(stream.mapToDouble(TireDotGridResponse::getAverageOfPurchasePrice).average().orElse(0))
                            .theNumberOfActiveDots(stream.filter(tireDotGridResponse -> tireDotGridResponse.getSumOfStock() > 0).count())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<TireDotGridResponse> findTireDotGridsByTireId(Long tireId) {
        return gridRepository.findTireDotGridsByTireId(tireId);
    }

    public List<PurchaseContentGridResponse> findAllPurchaseContentGrids(LocalDate from, LocalDate to) {
        List<PurchaseContentGridResponse> purchaseContentGridResponses = gridRepository.findPurchaseContentGrids(from, to);
        return setTireDotGridToPurchaseContentGrids(purchaseContentGridResponses);
    }

    public List<PurchaseContentGridResponse> findPurchaseContentGridsByPurchaseId(Long purchaseId) {
        List<PurchaseContentGridResponse> purchaseContentGridResponses = gridRepository.findPurchaseContentGridsByPurchaseId(purchaseId);
        return setTireDotGridToPurchaseContentGrids(purchaseContentGridResponses);
    }

    private List<PurchaseContentGridResponse> setTireDotGridToPurchaseContentGrids(List<PurchaseContentGridResponse> purchaseContentGridResponses) {
        List<Long> tireDotIds = purchaseContentGridResponses.stream()
                .map(PurchaseContentGridResponse::getTireDotId)
                .collect(Collectors.toList());
        Map<Long, TireDotGridResponse> tireDotGridResponseMap = gridRepository.findTireDotGridsByTireDotIdsIn(tireDotIds).stream()
                .collect(Collectors.toMap(TireDotGridResponse::getTireDotId, tireDotGridResponse -> tireDotGridResponse));

        return purchaseContentGridResponses.stream()
                .map(purchaseContentGridResponse -> purchaseContentGridResponse.setTireDotGrid(tireDotGridResponseMap.get(purchaseContentGridResponse.getTireDotId())))
                .collect(Collectors.toList());
    }

    public List<SaleContentGridResponse> findSaleContentGrids(SaleStatus saleStatus, SaleSource saleSource, SaleDateType saleDateType, LocalDate from, LocalDate to) {
        List<SaleContentGridResponse> saleContentGridResponses = gridRepository.findSaleContentGrids(saleStatus, saleSource, saleDateType, from, to);
        return setTireDotGridToSaleContentGrids(saleContentGridResponses);
    }

    public List<SaleContentGridResponse> findSaleContentsBySaleId(Long saleId) {
        List<SaleContentGridResponse> saleContentGridResponses = gridRepository.findSaleContentGridsBySaleId(saleId);
        return setTireDotGridToSaleContentGrids(saleContentGridResponses);
    }

    private List<SaleContentGridResponse> setTireDotGridToSaleContentGrids(List<SaleContentGridResponse> saleContentGridResponses) {
        List<Long> tireDotIds = saleContentGridResponses.stream()
                .map(SaleContentGridResponse::getTireDotId)
                .collect(Collectors.toList());
        Map<Long, TireDotGridResponse> tireDotGridResponseMap = gridRepository.findTireDotGridsByTireDotIdsIn(tireDotIds).stream()
                .collect(Collectors.toMap(TireDotGridResponse::getTireDotId, tireDotGridResponse -> tireDotGridResponse));

        return saleContentGridResponses.stream()
                .map(saleContentGridResponse -> saleContentGridResponse.setTireDotGrid(tireDotGridResponseMap.get(saleContentGridResponse.getTireDotId())))
                .collect(Collectors.toList());
    }
}
