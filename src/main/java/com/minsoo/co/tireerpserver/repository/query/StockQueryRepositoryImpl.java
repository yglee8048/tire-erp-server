package com.minsoo.co.tireerpserver.repository.query;

import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import com.minsoo.co.tireerpserver.model.entity.QTireDot;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.minsoo.co.tireerpserver.model.entity.QBrand.*;
import static com.minsoo.co.tireerpserver.model.entity.QPurchase.*;
import static com.minsoo.co.tireerpserver.model.entity.QStock.*;
import static com.minsoo.co.tireerpserver.model.entity.QTire.*;
import static com.minsoo.co.tireerpserver.model.entity.QTireDot.*;

@Repository
public class StockQueryRepositoryImpl implements StockQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StockQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private JPAQuery<TireStockResponse> createTireStockQuery() {
        QTireDot tireDotSub = new QTireDot("tireDotSub");   // for sub query
        return queryFactory
                .select(Projections.fields(TireStockResponse.class,
                        Projections.fields(TireResponse.class,
                                tire.id.as("tireId"),
                                Projections.fields(BrandSimpleResponse.class,
                                        brand.id.as("brandId"),
                                        brand.name.as("name")).as("brand"),
                                tire.productId,
                                tire.label,
                                tire.width,
                                tire.flatnessRatio,
                                tire.inch,
                                tire.size,
                                tire.pattern,
                                tire.loadIndex,
                                tire.speedIndex,
                                tire.season,
                                tire.price,
                                tire.runFlat,
                                tire.option,
                                tire.oe
                        ).as("tire"),
                        ExpressionUtils.as(JPAExpressions.select(purchase.price.avg())
                                .from(purchase)
                                .join(purchase.tireDot, tireDotSub)
                                .where(tireDotSub.tire.id.eq(tire.id)), "averageOfPurchasePrice"),  // 없으면 null
                        stock.quantity.sum().coalesce(0L).as("sumOfStock"), // 없으면 0
                        tireDot.count().coalesce(0L).as("numberOfDot")))    // 없으면 0
                .from(tire)
                .join(tire.brand, brand)
                .leftJoin(tire.tireDots, tireDot)
                .leftJoin(stock).on(tireDot.id.eq(stock.tireDot.id))
                .groupBy(tire.id)
                .orderBy(stock.quantity.sum().coalesce(0L).desc(), tire.productId.asc());
    }

    @Override
    public List<TireStockResponse> findTireStocksByParams(String size, String brandName, String pattern, String productId) {
        return createTireStockQuery()
                .where(tireSizeEq(size), brandNameContains(brandName), patternContains(pattern), productIdContains(productId))
                .fetch();
    }

    @Override
    public Optional<TireStockResponse> findTireStocksByTireId(Long tireId) {
        return Optional.ofNullable(
                createTireStockQuery()
                        .where(tire.id.eq(tireId))
                        .fetchOne());
    }

    private Predicate tireSizeEq(String size) {
        return size == null ? null : tire.size.contains(size);
    }

    private Predicate brandNameContains(String brandName) {
        return brandName == null ? null : brand.name.contains(brandName);
    }

    private Predicate patternContains(String pattern) {
        return pattern == null ? null : tire.pattern.contains(pattern);
    }

    private Predicate productIdContains(String productId) {
        return productId == null ? null : tire.productId.contains(productId);
    }
}
