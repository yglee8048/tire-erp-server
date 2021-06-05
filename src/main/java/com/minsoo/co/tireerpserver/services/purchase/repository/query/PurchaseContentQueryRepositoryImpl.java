package com.minsoo.co.tireerpserver.services.purchase.repository.query;

import com.minsoo.co.tireerpserver.api.v1.model.dto.purchase.content.PurchaseContentResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.minsoo.co.tireerpserver.services.purchase.entity.QPurchaseContent.purchaseContent;

@Repository
@RequiredArgsConstructor
public class PurchaseContentQueryRepositoryImpl implements PurchaseContentQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PurchaseContentResponse> findPurchaseContents(LocalDate from, LocalDate to) {
        return queryFactory.select(Projections.fields(PurchaseContentResponse.class,
                purchaseContent.id.as("purchaseContentId"),
                purchaseContent.price,
                purchaseContent.quantity))
                .from(purchaseContent)
                .fetch();
    }
}
