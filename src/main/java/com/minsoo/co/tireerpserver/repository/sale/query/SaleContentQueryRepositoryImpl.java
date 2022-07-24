package com.minsoo.co.tireerpserver.repository.sale.query;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.exception.InternalServerException;
import com.minsoo.co.tireerpserver.model.request.sale.SaleDateType;
import com.minsoo.co.tireerpserver.model.response.client.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.model.response.sale.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireDotGridResponse;
import com.minsoo.co.tireerpserver.repository.JPAQuerySnippet;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.minsoo.co.tireerpserver.entity.client.QClientCompany.clientCompany;
import static com.minsoo.co.tireerpserver.entity.management.QBrand.brand;
import static com.minsoo.co.tireerpserver.entity.management.QPattern.pattern;
import static com.minsoo.co.tireerpserver.entity.management.QWarehouse.warehouse;
import static com.minsoo.co.tireerpserver.entity.rank.QRank.rank;
import static com.minsoo.co.tireerpserver.entity.rank.QRankDotPrice.rankDotPrice;
import static com.minsoo.co.tireerpserver.entity.sale.QDelivery.delivery;
import static com.minsoo.co.tireerpserver.entity.sale.QSale.sale;
import static com.minsoo.co.tireerpserver.entity.sale.QSaleContent.saleContent;
import static com.minsoo.co.tireerpserver.entity.stock.QStock.stock;
import static com.minsoo.co.tireerpserver.entity.tire.QTire.tire;
import static com.minsoo.co.tireerpserver.entity.tire.QTireDot.tireDot;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SaleContentQueryRepositoryImpl implements SaleContentQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final TireDotRepository tireDotRepository;

    @Override
    public List<SaleContentGridResponse> findAllSaleContentGrids(SaleStatus saleStatus, SaleSource saleSource, SaleDateType saleDateType, LocalDate from, LocalDate to) {
        List<SaleContentGridResponse> saleContentGrids = selectSaleContentGridsQuery()
                .where(saleStatus == null ? null : sale.status.eq(saleStatus),
                        saleSource == null ? null : sale.source.eq(saleSource),
                        saleDateCondition(saleDateType, from, to))
                .fetch();

        if (CollectionUtils.isEmpty(saleContentGrids)) {
            return new ArrayList<>();
        }
        return setTireDotGridToSaleContentGrids(saleContentGrids, null);
    }

    @Override
    public List<SaleContentGridResponse> findSaleContentGridsByClientCompanyId(Long clientCompanyId, SaleStatus saleStatus, SaleSource saleSource, SaleDateType saleDateType, LocalDate from, LocalDate to) {
        List<SaleContentGridResponse> saleContentGrids = selectSaleContentGridsQuery()
                .where(clientCompany.id.eq(clientCompanyId),
                        saleStatus == null ? null : sale.status.eq(saleStatus),
                        saleSource == null ? null : sale.source.eq(saleSource),
                        saleDateCondition(saleDateType, from, to))
                .fetch();

        if (CollectionUtils.isEmpty(saleContentGrids)) {
            return new ArrayList<>();
        }
        Long rankId = getAnyRankIdFromContentGrids(saleContentGrids);
        return setTireDotGridToSaleContentGrids(saleContentGrids, rankId);
    }

    @Override
    public List<SaleContentGridResponse> findSaleContentGridsBySaleId(Long saleId) {
        List<SaleContentGridResponse> saleContentGrids = selectSaleContentGridsQuery()
                .where(sale.id.eq(saleId))
                .fetch();

        if (CollectionUtils.isEmpty(saleContentGrids)) {
            return new ArrayList<>();
        }
        Long rankId = getAnyRankIdFromContentGrids(saleContentGrids);
        return setTireDotGridToSaleContentGrids(saleContentGrids, rankId);
    }

    private Long getAnyRankIdFromContentGrids(List<SaleContentGridResponse> saleContentGrids) {
        return saleContentGrids.stream()
                .map(SaleContentGridResponse::getClientCompany)
                .map(ClientCompanyResponse::getRankId)
                .findAny()
                .orElseThrow(() -> {
                    log.error("The rank does not exist in client company of sale");
                    return new InternalServerException();
                });
    }

    private JPAQuery<SaleContentGridResponse> selectSaleContentGridsQuery() {
        return queryFactory
                .select(JPAQuerySnippet.saleContentGridResponse(sale, saleContent, delivery, tire, tireDot, pattern, brand, clientCompany, stock, warehouse))
                .from(saleContent)
                .join(sale).on(saleContent.sale.eq(sale))
                .join(delivery).on(sale.delivery.eq(delivery))
                .join(clientCompany).on(sale.clientCompany.eq(clientCompany))
                .join(rank).on(clientCompany.rank.eq(rank))
                .join(stock).on(saleContent.stock.eq(stock))
                .join(warehouse).on(stock.warehouse.eq(warehouse))
                .join(tireDot).on(saleContent.tireDot.eq(tireDot))
                .leftJoin(rankDotPrice).on(rankDotPrice.rank.eq(rank), rankDotPrice.tireDot.eq(tireDot))
                .join(tire).on(tireDot.tire.eq(tire))
                .join(pattern).on(tire.pattern.eq(pattern))
                .join(brand).on(pattern.brand.eq(brand))
                .orderBy(sale.id.desc());
    }

    private BooleanExpression saleDateCondition(SaleDateType saleDateType, LocalDate from, LocalDate to) {
        if (saleDateType == null) {
            return null;
        }
        switch (saleDateType) {
            case DESIRED_DELIVERY:
                return sale.desiredDeliveryDate.between(from, to);
            case TRANSACTION:
                return sale.transactionDate.between(from, to);
            case RELEASE:
                return sale.releaseDate.between(from, to);
            default:
                return null;
        }
    }

    private List<SaleContentGridResponse> setTireDotGridToSaleContentGrids(List<SaleContentGridResponse> saleContentGrids, Long rankId) {
        List<Long> tireDotIds = saleContentGrids.stream()
                .map(SaleContentGridResponse::getTireDotId)
                .collect(Collectors.toList());
        Map<Long, TireDotGridResponse> tireDotGridResponseMap = getTireDotGridMap(tireDotIds, rankId);

        return saleContentGrids.stream()
                .map(saleContentGrid -> saleContentGrid.setTireDotInfo(tireDotGridResponseMap.get(saleContentGrid.getTireDotId())))
                .collect(Collectors.toList());
    }

    private Map<Long, TireDotGridResponse> getTireDotGridMap(List<Long> tireDotIds, Long rankId) {
        return tireDotRepository.findTireDotGridsByTireDotIdsIn(tireDotIds, rankId)
                .stream()
                .collect(Collectors.toMap(tireDotGrid -> tireDotGrid.getTireDot().getTireDotId(), tireDotGrid -> tireDotGrid));
    }
}
