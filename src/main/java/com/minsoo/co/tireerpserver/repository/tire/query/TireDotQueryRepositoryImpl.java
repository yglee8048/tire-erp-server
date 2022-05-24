package com.minsoo.co.tireerpserver.repository.tire.query;

import com.minsoo.co.tireerpserver.model.response.tire.TireDotGridResponse;
import com.minsoo.co.tireerpserver.repository.JPAQuerySnippet;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.minsoo.co.tireerpserver.entity.purchase.QPurchaseContent.purchaseContent;
import static com.minsoo.co.tireerpserver.entity.rank.QRankDotPrice.rankDotPrice;
import static com.minsoo.co.tireerpserver.entity.stock.QStock.stock;
import static com.minsoo.co.tireerpserver.entity.tire.QTire.tire;
import static com.minsoo.co.tireerpserver.entity.tire.QTireDot.tireDot;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TireDotQueryRepositoryImpl implements TireDotQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TireDotGridResponse> findAllTireDotGrids() {
        return selectTireDotInfoQueryByOptionalRankId(null)
                .fetch();
    }

    @Override
    public List<TireDotGridResponse> findTireDotGridsByTireIdAndOptionalRankId(Long tireId, Long rankId) {
        return selectTireDotInfoQueryByOptionalRankId(rankId)
                .where(tireDot.tire.id.eq(tireId))
                .fetch();
    }

    @Override
    public List<TireDotGridResponse> findTireDotGridsByTireDotIdsIn(List<Long> tireDotIds, Long rankId) {
        return selectTireDotInfoQueryByOptionalRankId(rankId)
                .where(tireDot.id.in(tireDotIds))
                .fetch();
    }

    private JPAQuery<TireDotGridResponse> selectTireDotInfoQueryByOptionalRankId(Long rankId) {
        if (rankId == null) {
            return queryFactory
                    .select(JPAQuerySnippet.tireDotGridResponseExceptRank(tire, tireDot, stock, purchaseContent))
                    .from(tireDot)
                    .join(tire).on(tireDot.tire.eq(tire))
                    .leftJoin(stock).on(stock.tireDot.eq(tireDot))
                    .groupBy(tireDot);
        }
        return queryFactory
                .select(JPAQuerySnippet.tireDotGridResponse(tire, tireDot, rankDotPrice, stock, purchaseContent))
                .from(tireDot)
                .join(tire).on(tireDot.tire.eq(tire))
                .leftJoin(stock).on(stock.tireDot.eq(tireDot))
                .join(rankDotPrice).on(tireDot.eq(rankDotPrice.tireDot).and(rankDotPrice.rank.id.eq(rankId)))
                .groupBy(tireDot);
    }
}
