package com.minsoo.co.tireerpserver.service.grid;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.model.TireStandardDTO;
import com.minsoo.co.tireerpserver.model.request.sale.SaleDateType;
import com.minsoo.co.tireerpserver.model.response.grid.PurchaseContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.TireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.TireGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.customer.CustomerSaleContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.customer.CustomerTireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.customer.CustomerTireGridResponse;
import com.minsoo.co.tireerpserver.repository.grid.GridRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GridService {

    private final GridRepository gridRepository;

    public List<TireGridResponse> findAllTireGrids() {
        Map<Long, List<TireDotGridResponse>> tireDotGridMap = gridRepository.findAllTireDotGrids().stream()
                .collect(Collectors.groupingBy(TireDotGridResponse::getTireId));

        List<TireStandardDTO> allTireStandardDTOs = gridRepository.findAllTireStandardDTOs();
        return allTireStandardDTOs.stream()
                .collect(Collectors.toMap(TireStandardDTO::getTireId, tireStandardDTO -> tireStandardDTO))
                .entrySet().stream()
                .map(tireStandardDTOEntry -> {
                    List<TireDotGridResponse> tireDotGridResponses = Optional.ofNullable(tireDotGridMap.get(tireStandardDTOEntry.getKey())).orElse(Collections.emptyList());
                    return TireGridResponse.builder()
                            .tireInfo(tireStandardDTOEntry.getValue())
                            .sumOfOpenedStock(tireDotGridResponses.stream().mapToInt(TireDotGridResponse::getSumOfOpenedStock).sum())
                            .sumOfStock(tireDotGridResponses.stream().mapToInt(TireDotGridResponse::getSumOfStock).sum())
                            .averageOfPurchasePrice(tireDotGridResponses.stream().mapToDouble(TireDotGridResponse::getAverageOfPurchasePrice).average().orElse(0))
                            .theNumberOfActiveDots((int) tireDotGridResponses.stream().filter(tireDotGridResponse -> tireDotGridResponse.getSumOfStock() > 0).count())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<CustomerTireGridResponse> findAllCustomerTireGrids() {
        return findAllTireGrids().stream()
                .map(CustomerTireGridResponse::new)
                .collect(Collectors.toList());
    }

    public List<TireDotGridResponse> findTireDotGridsByTireId(Long tireId) {
        return gridRepository.findTireDotGridsByTireId(tireId);
    }

    public List<CustomerTireDotGridResponse> findCustomerTireDotGridsByTireIdAndRankId(Long tireId, Long rankId) {
        return gridRepository.findCustomerTireDotGirdsByTireIdAndRankId(tireId, rankId);
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
        Map<Long, TireDotGridResponse> tireDotGridResponseMap = getTireDotGridMap(tireDotIds);

        return purchaseContentGridResponses.stream()
                .map(purchaseContentGridResponse -> purchaseContentGridResponse.setTireDotGrid(tireDotGridResponseMap.get(purchaseContentGridResponse.getTireDotId())))
                .collect(Collectors.toList());
    }

    public List<SaleContentGridResponse> findSaleContentGrids(SaleStatus saleStatus, SaleSource saleSource, SaleDateType saleDateType, LocalDate from, LocalDate to) {
        List<SaleContentGridResponse> saleContentGridResponses = gridRepository.findSaleContentGrids(saleStatus, saleSource, saleDateType, from, to);
        return setTireDotGridToSaleContentGrids(saleContentGridResponses);
    }

    public List<CustomerSaleContentGridResponse> findCustomerSaleContentGridsByClientCompanyId(Long clientCompanyId, SaleStatus saleStatus, SaleSource saleSource, SaleDateType saleDateType, LocalDate from, LocalDate to) {
        List<SaleContentGridResponse> saleContentGridResponses = gridRepository.findSaleContentGrids(saleStatus, saleSource, saleDateType, from, to);
        return setTireDotGridToSaleContentGrids(saleContentGridResponses).stream()
                .map(CustomerSaleContentGridResponse::new)
                .collect(Collectors.toList());
    }

    public List<SaleContentGridResponse> findSaleContentsBySaleId(Long saleId) {
        List<SaleContentGridResponse> saleContentGridResponses = gridRepository.findSaleContentGridsBySaleId(saleId);
        return setTireDotGridToSaleContentGrids(saleContentGridResponses);
    }

    private List<SaleContentGridResponse> setTireDotGridToSaleContentGrids(List<SaleContentGridResponse> saleContentGridResponses) {
        List<Long> tireDotIds = saleContentGridResponses.stream()
                .map(SaleContentGridResponse::getTireDotId)
                .collect(Collectors.toList());
        Map<Long, TireDotGridResponse> tireDotGridResponseMap = getTireDotGridMap(tireDotIds);

        return saleContentGridResponses.stream()
                .map(saleContentGridResponse -> saleContentGridResponse.setTireDotGrid(tireDotGridResponseMap.get(saleContentGridResponse.getTireDotId())))
                .collect(Collectors.toList());
    }

    private Map<Long, TireDotGridResponse> getTireDotGridMap(List<Long> tireDotIds) {
        return gridRepository.findTireDotGridsByTireDotIdsIn(tireDotIds)
                .stream()
                .collect(Collectors.toMap(TireDotGridResponse::getTireDotId, tireDotGridResponse -> tireDotGridResponse));
    }
}
