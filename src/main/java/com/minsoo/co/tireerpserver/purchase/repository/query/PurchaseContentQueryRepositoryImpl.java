package com.minsoo.co.tireerpserver.purchase.repository.query;

import com.minsoo.co.tireerpserver.purchase.model.content.PurchaseContentGridResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.minsoo.co.tireerpserver.management.entity.QBrand.brand;
import static com.minsoo.co.tireerpserver.management.entity.QPattern.pattern;
import static com.minsoo.co.tireerpserver.management.entity.QVendor.vendor;
import static com.minsoo.co.tireerpserver.purchase.entity.QPurchase.purchase;
import static com.minsoo.co.tireerpserver.purchase.entity.QPurchaseContent.purchaseContent;
import static com.minsoo.co.tireerpserver.tire.entity.QTire.tire;
import static com.minsoo.co.tireerpserver.tire.entity.QTireDot.tireDot;

@Repository
@RequiredArgsConstructor
public class PurchaseContentQueryRepositoryImpl implements PurchaseContentQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PurchaseContentGridResponse> findPurchaseContents(LocalDate from, LocalDate to) {
        return queryFactory.select(Projections.fields(PurchaseContentGridResponse.class,
                purchaseContent.id,
                purchase.id.as("purchaseId"),
                vendor.id.as("vendorId"),
                tireDot.id.as("tireDotID"),
                tire.id.as("tireId"),
                pattern.id.as("patternId"),
                brand.id.as("brandId"),
                vendor.name.as("vendorName"),
                purchase.status.as("purchaseStatus"),
                purchase.transactionDate,
                brand.name.as("brandName"),
                pattern.name.as("patternName"),
                tire.tireIdentification,
                tire.size.as("tireSize"),
                tireDot.dot,
                purchaseContent.price.as("purchaseContentPrice"),
                purchaseContent.quantity.as("purchaseContentQuantity")))
                .from(purchaseContent)
                .join(purchaseContent.purchase, purchase)
                .join(purchase.vendor, vendor)
                .join(purchaseContent.tireDot, tireDot)
                .join(tireDot.tire, tire)
                .join(tire.pattern, pattern)
                .join(pattern.brand, brand)
                .where(fromDate(from), toDate(to))
                .fetch();
    }

    private Predicate fromDate(LocalDate from) {
        return from == null ? null : purchase.transactionDate.after(from.minusDays(1));
    }

    private Predicate toDate(LocalDate to) {
        return to == null ? null : purchase.transactionDate.before(to.plusDays(1));
    }
}
