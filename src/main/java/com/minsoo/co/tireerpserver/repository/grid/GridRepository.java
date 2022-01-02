package com.minsoo.co.tireerpserver.repository.grid;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.model.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.model.TireStandardDTO;
import com.minsoo.co.tireerpserver.model.request.sale.SaleDateType;
import com.minsoo.co.tireerpserver.model.response.client.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.model.response.grid.PurchaseContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.TireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.grid.customer.CustomerTireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.management.VendorResponse;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.minsoo.co.tireerpserver.entity.client.QClientCompany.clientCompany;
import static com.minsoo.co.tireerpserver.entity.management.QBrand.brand;
import static com.minsoo.co.tireerpserver.entity.management.QPattern.pattern;
import static com.minsoo.co.tireerpserver.entity.management.QVendor.vendor;
import static com.minsoo.co.tireerpserver.entity.management.QWarehouse.warehouse;
import static com.minsoo.co.tireerpserver.entity.purchase.QPurchase.purchase;
import static com.minsoo.co.tireerpserver.entity.purchase.QPurchaseContent.purchaseContent;
import static com.minsoo.co.tireerpserver.entity.rank.QRank.rank;
import static com.minsoo.co.tireerpserver.entity.rank.QRankDotPrice.rankDotPrice;
import static com.minsoo.co.tireerpserver.entity.sale.QSale.sale;
import static com.minsoo.co.tireerpserver.entity.sale.QSaleContent.saleContent;
import static com.minsoo.co.tireerpserver.entity.stock.QStock.stock;
import static com.minsoo.co.tireerpserver.entity.tire.QTire.tire;
import static com.minsoo.co.tireerpserver.entity.tire.QTireDot.tireDot;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GridRepository {

    private final JPAQueryFactory queryFactory;

    public List<TireStandardDTO> findAllTireStandardDTOs() {
        return queryFactory
                .select(tireStandardDTOFields())
                .from(tire)
                .join(pattern).on(tire.pattern.eq(pattern))
                .join(brand).on(pattern.brand.eq(brand))
                .fetch();
    }

    private QBean<TireStandardDTO> tireStandardDTOFields() {
        return Projections.fields(TireStandardDTO.class,
                tire.id.as("tireId"),
                tire.tireCode,
                brand.id.as("brandId"),
                brand.name.as("brandName"),
                tire.onSale,
                tire.width,
                tire.flatnessRatio,
                tire.inch,
                tire.size,
                tire.loadIndex,
                tire.speedIndex,
                pattern.id.as("patternId"),
                pattern.name.as("patternName"),
                tire.runFlat,
                tire.sponge,
                tire.sealing,
                tire.oe,
                tire.originalVehicle,
                tire.countryOfManufacture,
                tire.retailPrice,
                tire.tireGroup,
                tire.note,
                pattern.season,
                pattern.quietness,
                pattern.rideQuality,
                pattern.mileage,
                pattern.handling,
                pattern.breakingPower,
                pattern.sports,
                pattern.wetSurface,
                tire.pr,
                tire.lr,
                tire.tireRoId);
    }

    public List<TireDotGridResponse> findAllTireDotGrids() {
        return selectTireDotGridsQuery()
                .fetch();
    }

    public List<TireDotGridResponse> findTireDotGridsByTireId(Long tireId) {
        return selectTireDotGridsQuery()
                .where(tireDot.tire.id.eq(tireId))
                .fetch();
    }

    public List<TireDotGridResponse> findTireDotGridsByTireDotIdsIn(List<Long> tireDotIds) {
        return selectTireDotGridsQuery()
                .where(tireDot.id.in(tireDotIds))
                .fetch();
    }

    public List<PurchaseContentGridResponse> findPurchaseContentGrids(LocalDate from, LocalDate to) {
        return selectPurchaseContentGridsQuery()
                .where(from == null ? null : purchase.transactionDate.after(from),
                        to == null ? null : purchase.transactionDate.before(to))
                .fetch();
    }

    public List<PurchaseContentGridResponse> findPurchaseContentGridsByPurchaseId(Long purchaseId) {
        return selectPurchaseContentGridsQuery()
                .where(purchase.id.eq(purchaseId))
                .fetch();
    }

    public List<SaleContentGridResponse> findSaleContentGrids(SaleStatus saleStatus, SaleSource saleSource, SaleDateType saleDateType, LocalDate from, LocalDate to) {
        return selectSaleContentGridsQuery()
                .where(saleStatus == null ? null : sale.status.eq(saleStatus),
                        saleSource == null ? null : sale.source.eq(saleSource),
                        saleDateCondition(saleDateType, from, to))
                .fetch();
    }

    public List<SaleContentGridResponse> findSaleContentGridsByClientCompanyId(Long clientCompanyId, SaleStatus saleStatus, SaleSource saleSource, SaleDateType saleDateType, LocalDate from, LocalDate to) {
        return selectSaleContentGridsQuery()
                .where(clientCompany.id.eq(clientCompanyId),
                        saleStatus == null ? null : sale.status.eq(saleStatus),
                        saleSource == null ? null : sale.source.eq(saleSource),
                        saleDateCondition(saleDateType, from, to))
                .fetch();
    }

    public List<CustomerTireDotGridResponse> findCustomerTireDotGirdsByTireIdAndRankId(Long tireId, Long rankId) {
        return queryFactory
                .select(Projections.fields(CustomerTireDotGridResponse.class,
                        tireDot.id.as("tireDotId"),
                        tireDot.tire.id.as("tireId"),
                        tireDot.dot,
                        rankDotPrice.price.coalesce(tire.retailPrice),
                        getSumOfOpenedStock().as("sumOfOpenedStock")
                ))
                .from(tireDot)
                .join(tire).on(tireDot.tire.eq(tire))
                .leftJoin(rankDotPrice).on(rankDotPrice.tireDot.eq(tireDot))
                .where(tire.id.eq(tireId),
                        rankDotPrice.rank.id.eq(rankId))
                .fetch();
    }

    private BooleanExpression saleDateCondition(SaleDateType saleDateType, LocalDate from, LocalDate to) {
        if (saleDateType == null) {
            return null;
        }
        switch (saleDateType) {
            case DESIRED_DELIVERY:
                return sale.desiredDeliveryDate.after(from).and(sale.desiredDeliveryDate.before(to));
            case TRANSACTION:
                return sale.transactionDate.after(from).and(sale.transactionDate.before(to));
            case RELEASE:
                return sale.releaseDate.after(from).and(sale.releaseDate.before(to));
            default:
                return null;
        }
    }

    public List<SaleContentGridResponse> findSaleContentGridsBySaleId(Long saleId) {
        return selectSaleContentGridsQuery()
                .where(sale.id.eq(saleId))
                .fetch();
    }

    private JPAQuery<TireDotGridResponse> selectTireDotGridsQuery() {
        return queryFactory
                .select(Projections.fields(TireDotGridResponse.class,
                        tireDot.id.as("tireDotId"),
                        tireDot.tire.id.as("tireId"),
                        tireDot.dot,
                        getSumOfOpenedStock().as("sumOfOpenedStock"),
                        stock.quantity.sum().as("sumOfStock"),
                        ExpressionUtils.as(JPAExpressions
                                        .select(purchaseContent.price.avg())
                                        .from(purchaseContent)
                                        .where(purchaseContent.tireDot.eq(tireDot))
                                        .groupBy(tireDot),
                                "averageOfPurchasePrice")
                ))
                .from(tireDot)
                .leftJoin(stock).on(stock.tireDot.eq(tireDot))
                .groupBy(tireDot);
    }

    private NumberExpression<Integer> getSumOfOpenedStock() {
        return new CaseBuilder()
                .when(stock.lock.eq(false))
                .then(stock.quantity)
                .otherwise(0).sum();
    }

    private JPAQuery<PurchaseContentGridResponse> selectPurchaseContentGridsQuery() {
        return queryFactory
                .select(Projections.fields(PurchaseContentGridResponse.class,
                        purchaseContent.id.as("purchaseContentId"),
                        purchase.id.as("purchaseId"),
                        purchase.transactionDate,
                        Projections.constructor(VendorResponse.class, vendor).as("vendor"),
                        tireStandardDTOFields().as("tireInfo"),
                        tireDot.id.as("tireDotId"),
                        purchaseContent.quantity,
                        purchaseContent.price,
                        purchaseContent.quantity.longValue().multiply(purchaseContent.price).as("purchasePrice"),

                        stock.id.as("stockId"),
                        warehouse.id.as("warehouseId"),
                        warehouse.name.as("warehouseName"),

                        purchaseContent.createdAt,
                        purchaseContent.lastModifiedAt,
                        purchaseContent.createdBy,
                        purchaseContent.lastModifiedBy
                ))
                .from(purchaseContent)
                .join(purchase).on(purchaseContent.purchase.eq(purchase))
                .join(vendor).on(purchase.vendor.eq(vendor))
                .join(tireDot).on(purchaseContent.tireDot.eq(tireDot))
                .join(stock).on(purchaseContent.stock.eq(stock))
                .join(warehouse).on(stock.warehouse.eq(warehouse))
                .join(tire).on(tireDot.tire.eq(tire))
                .join(pattern).on(tire.pattern.eq(pattern))
                .join(brand).on(pattern.brand.eq(brand));
    }

    private JPAQuery<SaleContentGridResponse> selectSaleContentGridsQuery() {
        return queryFactory
                .select(Projections.fields(SaleContentGridResponse.class,
                        saleContent.id.as("saleContentId"),
                        sale.id.as("saleId"),
                        sale.transactionDate,
                        sale.releaseDate,
                        sale.desiredDeliveryDate,
                        sale.status,
                        Projections.fields(ClientCompanyResponse.class,
                                clientCompany.id.as("clientCompanyId"),
                                clientCompany.name,
                                rank.id.as("rankId"),
                                rank.name.as("rankName"),
                                clientCompany.description,
                                Projections.constructor(BusinessInfoDTO.class, clientCompany.businessInfo).as("businessInfo"),
                                clientCompany.createdBy,
                                clientCompany.createdAt,
                                clientCompany.lastModifiedBy,
                                clientCompany.lastModifiedAt).as("clientCompany"),
                        tireStandardDTOFields().as("tireInfo"),
                        tireDot.id.as("tireDotId"),
                        saleContent.quantity,
                        saleContent.price,
                        saleContent.quantity.longValue().multiply(saleContent.price).as("salePrice"),

                        stock.id.as("stockId"),
                        stock.nickname.as("stockNickname"),
                        warehouse.id.as("warehouseId"),
                        warehouse.name.as("warehouseName"),

                        saleContent.createdAt,
                        saleContent.lastModifiedAt,
                        saleContent.createdBy,
                        saleContent.lastModifiedBy
                ))
                .from(saleContent)
                .join(sale).on(saleContent.sale.eq(sale))
                .join(clientCompany).on(sale.clientCompany.eq(clientCompany))
                .join(rank).on(clientCompany.rank.eq(rank))
                .join(stock).on(saleContent.stock.eq(stock))
                .join(warehouse).on(stock.warehouse.eq(warehouse))
                .join(tireDot).on(saleContent.tireDot.eq(tireDot))
                .join(tire).on(tireDot.tire.eq(tire))
                .join(pattern).on(tire.pattern.eq(pattern))
                .join(brand).on(pattern.brand.eq(brand));
    }
}
