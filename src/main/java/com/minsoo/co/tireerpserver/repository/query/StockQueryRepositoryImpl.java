package com.minsoo.co.tireerpserver.repository.query;

import com.minsoo.co.tireerpserver.model.dto.management.tire.TireResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.minsoo.co.tireerpserver.model.entity.QBrand.*;
import static com.minsoo.co.tireerpserver.model.entity.QStock.*;
import static com.minsoo.co.tireerpserver.model.entity.QTire.*;

@Repository
public class StockQueryRepositoryImpl implements StockQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StockQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<TireStockResponse> findTireStocks(String size, String brandName, String pattern, String productId) {
        return null;
//        return queryFactory
//                .select(Projections.fields(TireStockResponse.class,
//                        Projections.fields(TireResponse.class,
//                                tire.id.as("tire_id"),
//                                brand.id.as("brand_id"),
//                                brand.name.as("brand_name"),
//                                tire.productId,
//                                tire.label,
//                                tire.width,
//                                tire.flatnessRatio,
//                                tire.inch,
//                                tire.loadIndex,
//                                tire.speedIndex,
//                                tire.season,
//                                tire.price,
//                                tire.runFlat,
//                                tire.option,
//                                tire.oe
//                        ).as("tire"),
//                        // TODO: 서브쿼리로 한 번에 긁어오기
//                        Expressions.as(Expressions.constant(0L), "averageOfPurchasePrice"),
//                        stock.quantity.sum().as("sumOfStock"),
//                        tireDot.count().as("numberOfDot")))
//                .from(tire)
//                .join(tire.brand, brand)
//                .leftJoin(tireDot.stocks, stock)
//                // TODO: equal 이 아니라 like 로 수정
//                .where(tireSizeEq(size), brandNameEq(brandName), patternEq(pattern), productIdEq(productId))
//                .groupBy(tire.id)
//                .fetch();
    }

    private Predicate tireSizeEq(String size) {
        return size == null ? null :
                tire.width.eq(Integer.valueOf(size.substring(0, 3)))    // width
                        .and(tire.flatnessRatio.eq(Integer.valueOf(size.substring(3, 5))))  // flatnessRatio
                        .and(tire.inch.eq(Integer.valueOf(size.substring(5, 7))));  // inch
    }

    private Predicate brandNameEq(String brandName) {
        return brandName == null ? null : brand.name.eq(brandName);
    }

    private Predicate patternEq(String pattern) {
        return pattern == null ? null : tire.pattern.eq(pattern);
    }

    private Predicate productIdEq(String productId) {
        return productId == null ? null : tire.productId.eq(productId);
    }
}
