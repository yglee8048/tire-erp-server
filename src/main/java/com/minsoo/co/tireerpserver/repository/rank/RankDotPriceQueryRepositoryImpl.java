package com.minsoo.co.tireerpserver.repository.rank;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.minsoo.co.tireerpserver.entity.rank.QRankDotPrice.rankDotPrice;
import static com.minsoo.co.tireerpserver.entity.stock.QStock.stock;
import static com.minsoo.co.tireerpserver.entity.tire.QTire.tire;
import static com.minsoo.co.tireerpserver.entity.tire.QTireDot.tireDot;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RankDotPriceQueryRepositoryImpl implements RankDotPriceQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long getPriceByTireDotIdAndClientId(Long tireDotId, Long rankId) {
        return queryFactory
                .select(rankDotPrice.price.coalesce(tire.retailPrice))
                .from(tireDot)
                .join(tire).on(tireDot.tire.eq(tire))
                .leftJoin(rankDotPrice).on(tireDot.eq(rankDotPrice.tireDot).and(rankDotPrice.rank.id.eq(rankId)))
                .where(tireDot.id.eq(tireDotId))
                .fetchOne();
    }

    private BooleanExpression hasStock(Long rankId) {
        if (rankId == null) {
            return null;
        }
        return new CaseBuilder()
                .when(stock.lock.eq(false))
                .then(stock.quantity)
                .otherwise(0).sum()
                .gt(0);
    }
}
