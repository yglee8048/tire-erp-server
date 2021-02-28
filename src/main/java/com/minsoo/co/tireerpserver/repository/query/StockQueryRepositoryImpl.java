package com.minsoo.co.tireerpserver.repository.query;

import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.TireResponse;
import com.minsoo.co.tireerpserver.model.entity.QTireDot;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    // TODO: 타이어 기준 레프트 조인으로 변경(재고가 없어도, 타이어가 목록에서 조회되어야 함)
    @Override
    public List<TireStockResponse> findTireStocks(String size, String brandName, String pattern, String productId) {
        // for sub query
        QTireDot tireDotSub = new QTireDot("tireDotSub");
        // query
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
                                .where(tireDotSub.tire.id.eq(tire.id)), "averageOfPurchasePrice"),  //TODO: 없으면 null
                        stock.quantity.sum().as("sumOfStock"),  //TODO: 없으면 0
                        tireDot.count().as("numberOfDot"))) //TODO: 없으면 0
                .from(stock)
                .join(stock.tireDot, tireDot)
                .join(tireDot.tire, tire)
                .join(tire.brand, brand)
                .where(tireSizeEq(size), brandNameContains(brandName), patternContains(pattern), productIdContains(productId))
                .groupBy(tire.id)
                .fetch();
    }

    private Predicate tireSizeEq(String size) {
        return size == null ? null :
                tire.width.stringValue().concat(tire.flatnessRatio.stringValue()).concat(tire.inch.stringValue()).contains(size);
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
