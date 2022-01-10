package com.minsoo.co.tireerpserver.repository.tire;

import com.minsoo.co.tireerpserver.model.response.tire.query.TireDotPriceResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.minsoo.co.tireerpserver.entity.rank.QRankDotPrice.rankDotPrice;
import static com.minsoo.co.tireerpserver.entity.tire.QTire.tire;
import static com.minsoo.co.tireerpserver.entity.tire.QTireDot.tireDot;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TireDotQueryRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public List<TireDotPriceResponse> findTireDotPricesByTireIdAndRankId(Long tireId, Long rankId) {
        return queryFactory
                .select(Projections.fields(TireDotPriceResponse.class,
                        tireDot.id.as("tireDotId"),
                        tire.id.as("tireId"),
                        tireDot.dot,
                        rankDotPrice.price.coalesce(tire.retailPrice).as("price"),
                        tireDot.createdAt,
                        tireDot.lastModifiedAt,
                        tireDot.createdBy,
                        tireDot.lastModifiedBy
                ))
                .from(tireDot)
                .join(tire).on(tireDot.tire.eq(tire))
                .leftJoin(rankDotPrice).on(rankDotPrice.tireDot.eq(tireDot))
                .where(tire.id.eq(tireId),
                        rankId == null ? null : rankDotPrice.rank.id.eq(rankId))
                .fetch();
    }
}
