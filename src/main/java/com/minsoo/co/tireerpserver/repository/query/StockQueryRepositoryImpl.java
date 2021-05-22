package com.minsoo.co.tireerpserver.repository.query;

import com.minsoo.co.tireerpserver.model.code.PatternOption;
import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
import com.minsoo.co.tireerpserver.model.dto.stock.TireStockResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.pattern.PatternResponse;
import com.minsoo.co.tireerpserver.model.dto.tire.tire.TireResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.minsoo.co.tireerpserver.model.entity.entities.management.QBrand.brand;
import static com.minsoo.co.tireerpserver.model.entity.entities.stock.QStock.stock;
import static com.minsoo.co.tireerpserver.model.entity.entities.tire.QPattern.pattern;
import static com.minsoo.co.tireerpserver.model.entity.entities.tire.QPatternOptions.patternOptions;
import static com.minsoo.co.tireerpserver.model.entity.entities.tire.QTire.tire;
import static com.minsoo.co.tireerpserver.model.entity.entities.tire.QTireDot.tireDot;
import static com.minsoo.co.tireerpserver.model.entity.entities.tire.QTireOptions.tireOptions;

@Repository
@RequiredArgsConstructor
public class StockQueryRepositoryImpl implements StockQueryRepository {

    private final JPAQueryFactory queryFactory;

    private JPAQuery<TireStockResponse> createTireStockQuery() {
        return queryFactory
                .select(Projections.fields(TireStockResponse.class,
                        Projections.fields(TireResponse.class,
                                tire.id.as("tireId"),
                                tire.productId,
                                Projections.fields(PatternResponse.class,
                                        pattern.id.as("patternId"),
                                        Projections.fields(BrandResponse.class,
                                                brand.id.as("brandId"),
                                                brand.name,
                                                brand.description).as("brand"),
                                        pattern.name,
                                        pattern.carType,
                                        pattern.rank,
                                        pattern.season).as("pattern"),
                                tire.width,
                                tire.flatnessRatio,
                                tire.inch,
                                tire.size,
                                tire.loadIndex,
                                tire.speedIndex,
                                tire.options,
                                tire.oe,
                                tire.countryOfManufacture,
                                tire.originalVehicle,
                                tire.note,
                                tire.group,
                                tire.pr,
                                tire.lr).as("tire"),
                        stock.quantity.sum().as("sumOfStock"),
                        new CaseBuilder().when(stock.lock.eq(true))
                                .then(0L)
                                .otherwise(stock.quantity).sum().as("sumOfOpenedStock"),
                        new CaseBuilder().when(stock.quantity.gt(0L))
                                .then(tireDot.id)
                                .otherwise(Expressions.nullExpression()).countDistinct().as("numberOfActiveDot")))
                .from(stock)
                .join(stock.tireDot, tireDot)
                .join(tireDot.tire, tire)
                .join(tire.pattern, pattern)
                .join(pattern.brand, brand)
                .groupBy(tire.id);
    }

    @Override
    public List<TireStockResponse> findTireStocks(String size, String brandName, String patternName, String productId) {
        return createTireStockQuery()
                .where(containsSize(size),
                        containsBrandName(brandName),
                        containsPatternName(patternName),
                        containsProductId(productId))
                .fetch();
    }

    @Override
    public Optional<TireStockResponse> findTireStocksByTireId(Long tireId) {
        List<TireStockResponse> results = createTireStockQuery().where(tire.id.eq(tireId)).fetch();
        if (CollectionUtils.isEmpty(results)) {
            return Optional.empty();
        }
        if (results.size() > 1) {
            throw new RuntimeException("findTireStocksByTireId 의 조회 결과가 2개 이상입니다.");
        }

        TireStockResponse tireStockResponse = results.get(0);
        tireStockResponse.getTire().setOptions(selectTireOptions(tireId));
        tireStockResponse.getTire().getPattern().setOptions(selectPatternOptions(tireStockResponse));
        return Optional.of(tireStockResponse);
    }

    private List<TireOption> selectTireOptions(Long tireId) {
        return queryFactory.select(tireOptions.option)
                .from(tireOptions)
                .where(tireOptions.tire.id.eq(tireId))
                .fetch();
    }

    private List<PatternOption> selectPatternOptions(TireStockResponse tireStockResponse) {
        return queryFactory.select(patternOptions.option)
                .from(patternOptions)
                .where(patternOptions.pattern.id.eq(tireStockResponse.getTire().getPattern().getPatternId())).fetch();
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

    private BooleanExpression containsProductId(String productId) {
        return StringUtils.hasText(productId) ? tire.productId.contains(productId) : null;
    }
}
