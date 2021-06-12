package com.minsoo.co.tireerpserver.stock.repository.query;

import com.minsoo.co.tireerpserver.management.model.brand.BrandResponse;
import com.minsoo.co.tireerpserver.stock.model.TireStockResponse;
import com.minsoo.co.tireerpserver.management.model.pattern.PatternSimpleResponse;
import com.minsoo.co.tireerpserver.tire.model.TireSimpleResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.minsoo.co.tireerpserver.management.entity.QBrand.brand;
import static com.minsoo.co.tireerpserver.management.entity.QPattern.pattern;
import static com.minsoo.co.tireerpserver.stock.entity.QStock.stock;
import static com.minsoo.co.tireerpserver.tire.entity.QTire.tire;
import static com.minsoo.co.tireerpserver.tire.entity.QTireDot.tireDot;

@Repository
@RequiredArgsConstructor
public class StockQueryRepositoryImpl implements StockQueryRepository {

    private final JPAQueryFactory queryFactory;

    private JPAQuery<TireStockResponse> createTireStockQuery() {
        return queryFactory
                .select(Projections.fields(TireStockResponse.class,
                        Projections.fields(TireSimpleResponse.class,
                                tire.id.as("tireId"),
                                tire.onSale,
                                tire.tireIdentification,
                                Projections.fields(PatternSimpleResponse.class,
                                        pattern.id.as("patternId"),
                                        Projections.fields(BrandResponse.class,
                                                brand.id.as("brandId"),
                                                brand.name,
                                                brand.description).as("brand"),
                                        pattern.name).as("pattern"),
                                tire.size,
                                tire.loadIndex,
                                tire.speedIndex).as("tire"),
                        stock.quantity.sum().as("sumOfStock"),
                        new CaseBuilder().when(stock.lock.eq(true))
                                .then(0L)
                                .otherwise(stock.quantity).sum().as("sumOfOpenedStock"),
                        new CaseBuilder().when(stock.quantity.gt(0L))
                                .then(tireDot.id)
                                .otherwise(Expressions.nullExpression()).countDistinct().as("numberOfActiveDot")))
                .from(tire)
                .join(tire.pattern, pattern)
                .join(pattern.brand, brand)
                .leftJoin(tire.tireDots, tireDot)
                .leftJoin(tireDot.stocks, stock)
                .groupBy(tire.id);
    }

    @Override
    public List<TireStockResponse> findTireStocks(String size, String brandName, String patternName, String productId) {
        return createTireStockQuery()
                .where(containsSize(size),
                        containsBrandName(brandName),
                        containsPatternName(patternName),
                        containsTireIdentification(productId))
                .fetch();
    }

    private BooleanExpression containsSize(String size) {
        return StringUtils.hasText(size) ? tire.size.contains(size) : null;
    }

    private BooleanExpression containsBrandName(String brandName) {
        return StringUtils.hasText(brandName) ? brand.name.contains(brandName) : null;
    }

    private BooleanExpression containsPatternName(String patternName) {
        return StringUtils.hasText(patternName) ? pattern.name.contains(patternName) : null;
    }

    private BooleanExpression containsTireIdentification(String tireIdentification) {
        return StringUtils.hasText(tireIdentification) ? tire.tireIdentification.contains(tireIdentification) : null;
    }
}
