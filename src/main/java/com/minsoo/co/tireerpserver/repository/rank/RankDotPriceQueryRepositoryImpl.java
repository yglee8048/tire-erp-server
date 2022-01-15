package com.minsoo.co.tireerpserver.repository.rank;

import com.minsoo.co.tireerpserver.model.response.rank.RankDotPriceGridResponse;
import com.minsoo.co.tireerpserver.repository.JPAQuerySnippet;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.minsoo.co.tireerpserver.entity.rank.QRank.rank;
import static com.minsoo.co.tireerpserver.entity.rank.QRankDotPrice.rankDotPrice;
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
                .select(JPAQuerySnippet.getRankDotPrice(tire, rankDotPrice))
                .from(tireDot)
                .join(tire).on(tireDot.tire.eq(tire))
                .leftJoin(rankDotPrice).on(tireDot.eq(rankDotPrice.tireDot).and(rankDotPrice.rank.id.eq(rankId)))
                .where(tireDot.id.eq(tireDotId))
                .fetchOne();
    }

    public List<RankDotPriceGridResponse> findRankDotPricesByTireDotId(Long tireDotId) {
        return queryFactory
                .select(JPAQuerySnippet.rankDotPriceResponse(rankDotPrice, rank, tireDot))
                .from(rankDotPrice)
                .join(rank).on(rankDotPrice.rank.eq(rank))
                .join(tireDot).on(rankDotPrice.tireDot.eq(tireDot))
                .where(tireDot.id.eq(tireDotId))
                .fetch();
    }
}
