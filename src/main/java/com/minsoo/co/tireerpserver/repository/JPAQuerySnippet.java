package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.entity.client.QClientCompany;
import com.minsoo.co.tireerpserver.entity.management.QBrand;
import com.minsoo.co.tireerpserver.entity.management.QPattern;
import com.minsoo.co.tireerpserver.entity.management.QVendor;
import com.minsoo.co.tireerpserver.entity.management.QWarehouse;
import com.minsoo.co.tireerpserver.entity.purchase.QPurchase;
import com.minsoo.co.tireerpserver.entity.purchase.QPurchaseContent;
import com.minsoo.co.tireerpserver.entity.rank.QRank;
import com.minsoo.co.tireerpserver.entity.rank.QRankDotPrice;
import com.minsoo.co.tireerpserver.entity.sale.QDelivery;
import com.minsoo.co.tireerpserver.entity.sale.QSale;
import com.minsoo.co.tireerpserver.entity.sale.QSaleContent;
import com.minsoo.co.tireerpserver.entity.stock.QStock;
import com.minsoo.co.tireerpserver.entity.tire.QTire;
import com.minsoo.co.tireerpserver.entity.tire.QTireDot;
import com.minsoo.co.tireerpserver.model.AddressDTO;
import com.minsoo.co.tireerpserver.model.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.model.TireInfoResponse;
import com.minsoo.co.tireerpserver.model.response.client.ClientCompanyResponse;
import com.minsoo.co.tireerpserver.model.response.management.PatternResponse;
import com.minsoo.co.tireerpserver.model.response.management.VendorResponse;
import com.minsoo.co.tireerpserver.model.response.management.WarehouseResponse;
import com.minsoo.co.tireerpserver.model.response.purchase.PurchaseContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.purchase.PurchaseResponse;
import com.minsoo.co.tireerpserver.model.response.rank.RankDotPriceGridResponse;
import com.minsoo.co.tireerpserver.model.response.rank.RankResponse;
import com.minsoo.co.tireerpserver.model.response.sale.DeliveryResponse;
import com.minsoo.co.tireerpserver.model.response.sale.SaleContentGridResponse;
import com.minsoo.co.tireerpserver.model.response.sale.SaleResponse;
import com.minsoo.co.tireerpserver.model.response.stock.StockGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireDotGridResponse;
import com.minsoo.co.tireerpserver.model.response.tire.TireDotResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

public class JPAQuerySnippet {

    public static QBean<PurchaseContentGridResponse> purchaseContentGridResponse(QPurchase purchase, QPurchaseContent purchaseContent,
                                                                                 QTire tire, QTireDot tireDot, QPattern pattern, QBrand brand,
                                                                                 QStock stock, QWarehouse warehouse, QVendor vendor) {
        return Projections.fields(PurchaseContentGridResponse.class,
                purchaseContent.id.as("purchaseContentId"),
                purchaseResponse(purchase).as("purchase"),

                vendorResponse(vendor).as("vendor"),

                tireInfoResponse(tire, pattern, brand).as("tireInfo"),

                tireDot.id.as("tireDotId"),

                purchaseContent.quantity,
                purchaseContent.price,
                purchaseContent.quantity.longValue().multiply(purchaseContent.price).as("purchasePrice"),

                stockGridResponse(stock, warehouse).as("stockInfo"),

                purchaseContent.createdAt,
                purchaseContent.lastModifiedAt,
                purchaseContent.createdBy,
                purchaseContent.lastModifiedBy
        );
    }

    public static QBean<SaleContentGridResponse> saleContentGridResponse(QSale sale, QSaleContent saleContent, QDelivery delivery,
                                                                         QTire tire, QTireDot tireDot, QPattern pattern, QBrand brand,
                                                                         QClientCompany clientCompany, QStock stock, QWarehouse warehouse) {
        return Projections.fields(SaleContentGridResponse.class,
                saleContent.id.as("saleContentId"),
                saleResponse(sale).as("sale"),

                clientCompanyResponse(clientCompany).as("clientCompany"),

                tireInfoResponse(tire, pattern, brand).as("tireInfo"),

                tireDot.id.as("tireDotId"),

                saleContent.quantity,
                saleContent.price,
                saleContent.quantity.longValue().multiply(saleContent.price).as("salePrice"),

                stockGridResponse(stock, warehouse).as("stockInfo"),

                deliveryResponse(delivery).as("delivery"),

                saleContent.createdAt,
                saleContent.lastModifiedAt,
                saleContent.createdBy,
                saleContent.lastModifiedBy
        );
    }

    public static QBean<TireDotGridResponse> tireDotGridResponse(QTire tire, QTireDot tireDot, QRankDotPrice rankDotPrice,
                                                                 QStock stock, QPurchaseContent purchaseContent) {

        return Projections.fields(TireDotGridResponse.class,
                tireDotResponse(tireDot).as("tireDot"),

                getRankDotPrice(tire, rankDotPrice).as("price"),

                getSumOfOpenedStock(stock).as("sumOfOpenedStock"),
                stock.quantity.sum().coalesce(0).as("sumOfStock"),

                ExpressionUtils.as(getAvgPurchasePrice(tireDot, purchaseContent), "averageOfPurchasePrice"),

                tireDot.createdAt,
                tireDot.lastModifiedAt,
                tireDot.createdBy,
                tireDot.lastModifiedBy
        );
    }

    private static JPQLQuery<Double> getAvgPurchasePrice(QTireDot tireDot, QPurchaseContent purchaseContent) {
        return JPAExpressions
                .select(purchaseContent.price.avg().coalesce(0d))
                .from(purchaseContent)
                .where(purchaseContent.tireDot.eq(tireDot))
                .groupBy(tireDot);
    }

    public static QBean<TireInfoResponse> tireInfoResponse(QTire tire, QPattern pattern, QBrand brand) {
        return Projections.fields(TireInfoResponse.class,
                tire.id.as("tireId"),

                brand.id.as("brandId"),
                brand.name.as("brandName"),

                pattern.id.as("patternId"),
                pattern.name.as("patternName"),
                pattern.englishName.as("patternEnglishName"),

                tire.width,
                tire.flatnessRatio,
                tire.inch,
                tire.size,
                tire.oe,
                tire.loadIndex,
                tire.speedIndex,

                tire.runFlat,
                tire.sponge,
                tire.sealing,

                tire.factoryPrice,
                tire.countryOfManufacture,

                pattern.season,
                pattern.quietness,
                pattern.rideQuality,
                pattern.mileage,
                pattern.handling,
                pattern.breakingPower,
                pattern.wetSurface,
                pattern.snowPerformance,

                tire.tireCode,

                tire.createdBy,
                tire.createdAt,
                tire.lastModifiedBy,
                tire.lastModifiedAt);
    }

    public static QBean<StockGridResponse> stockGridResponse(QStock stock, QWarehouse warehouse) {
        return Projections.fields(StockGridResponse.class,
                stock.id.as("stockId"),
                stock.nickname.as("nickname"),
                stock.tireDot.id.as("tireDotId"),
                warehouseResponse(warehouse).as("warehouse"),
                stock.quantity,
                stock.lock,
                stock.createdAt,
                stock.lastModifiedAt,
                stock.createdBy,
                stock.lastModifiedBy);
    }

    public static QBean<RankDotPriceGridResponse> rankDotPriceResponse(QRankDotPrice rankDotPrice, QRank rank, QTireDot tireDot) {
        return Projections.fields(RankDotPriceGridResponse.class,
                rankResponse(rank).as("rank"),
                tireDotResponse(tireDot).as("tireDot"),
                rankDotPrice.discountRate);
    }

    public static QBean<ClientCompanyResponse> clientCompanyResponse(QClientCompany clientCompany) {
        return Projections.fields(ClientCompanyResponse.class,
                clientCompany.id.as("clientCompanyId"),
                clientCompany.name,
                clientCompany.description,
                Projections.constructor(BusinessInfoDTO.class, clientCompany.businessInfo).as("businessInfo"),
                clientCompany.rank.id.as("rankId"),
                clientCompany.createdBy,
                clientCompany.createdAt,
                clientCompany.lastModifiedBy,
                clientCompany.lastModifiedAt);
    }

    public static QBean<DeliveryResponse> deliveryResponse(QDelivery delivery) {
        return Projections.fields(DeliveryResponse.class,
                delivery.id.as("deliveryId"),
                delivery.recipientName.as("recipientName"),
                Projections.constructor(AddressDTO.class, delivery.address).as("address"),
                delivery.recipientPhoneNumber,
                delivery.deliveryOption,
                delivery.deliveryCompany,
                delivery.invoiceNumber,
                delivery.createdAt,
                delivery.lastModifiedAt,
                delivery.createdBy,
                delivery.lastModifiedBy);
    }

    public static QBean<WarehouseResponse> warehouseResponse(QWarehouse warehouse) {
        return Projections.fields(WarehouseResponse.class,
                warehouse.id.as("warehouseId"),
                warehouse.name,
                warehouse.description,
                Projections.constructor(AddressDTO.class, warehouse.address).as("address"),
                warehouse.createdBy,
                warehouse.createdAt,
                warehouse.lastModifiedBy,
                warehouse.lastModifiedAt);
    }

    public static QBean<VendorResponse> vendorResponse(QVendor vendor) {
        return Projections.fields(VendorResponse.class,
                vendor.id.as("vendorId"),
                vendor.name,
                vendor.description,
                Projections.constructor(BusinessInfoDTO.class, vendor.businessInfo).as("businessInfo"),
                vendor.createdBy,
                vendor.createdAt,
                vendor.lastModifiedBy,
                vendor.lastModifiedAt);
    }

    public static NumberExpression<Long> getRankDotPrice(QTire tire, QRankDotPrice rankDotPrice) {
        return MathExpressions.round(tire.factoryPrice.multiply(rankDotPrice.discountRate.coalesce(1F)), 3);
    }

    public static ConstructorExpression<SaleResponse> saleResponse(QSale sale) {
        return Projections.constructor(SaleResponse.class, sale);
    }

    public static ConstructorExpression<PurchaseResponse> purchaseResponse(QPurchase purchase) {
        return Projections.constructor(PurchaseResponse.class, purchase);
    }

    public static ConstructorExpression<TireDotResponse> tireDotResponse(QTireDot tireDot) {
        return Projections.constructor(TireDotResponse.class, tireDot);
    }

    public static ConstructorExpression<PatternResponse> patternResponse(QPattern pattern) {
        return Projections.constructor(PatternResponse.class, pattern);
    }

    public static ConstructorExpression<RankResponse> rankResponse(QRank rank) {
        return Projections.constructor(RankResponse.class, rank);
    }

    private static NumberExpression<Integer> getSumOfOpenedStock(QStock stock) {
        return new CaseBuilder()
                .when(stock.lock.eq(false))
                .then(stock.quantity)
                .otherwise(0).sum();
    }
}
