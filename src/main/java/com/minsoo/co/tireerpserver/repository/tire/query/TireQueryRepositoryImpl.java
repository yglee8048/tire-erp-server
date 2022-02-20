package com.minsoo.co.tireerpserver.repository.tire.query;

import com.minsoo.co.tireerpserver.model.TireInfoResponse;
import com.minsoo.co.tireerpserver.repository.JPAQuerySnippet;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.minsoo.co.tireerpserver.entity.management.QBrand.brand;
import static com.minsoo.co.tireerpserver.entity.management.QPattern.pattern;
import static com.minsoo.co.tireerpserver.entity.tire.QTire.tire;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TireQueryRepositoryImpl implements TireQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TireInfoResponse> findAllTireInfos() {
        return queryFactory
                .select(JPAQuerySnippet.tireInfoResponse(tire, pattern, brand))
                .from(tire)
                .join(pattern).on(tire.pattern.eq(pattern))
                .join(brand).on(pattern.brand.eq(brand))
                .fetch();
    }
}
