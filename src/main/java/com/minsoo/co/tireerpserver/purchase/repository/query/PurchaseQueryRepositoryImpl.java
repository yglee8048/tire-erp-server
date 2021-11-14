package com.minsoo.co.tireerpserver.purchase.repository.query;

import com.minsoo.co.tireerpserver.purchase.model.PurchaseResponse;
import com.minsoo.co.tireerpserver.purchase.model.content.PurchaseContentResponse;
import com.minsoo.co.tireerpserver.shared.model.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.management.model.brand.BrandResponse;
import com.minsoo.co.tireerpserver.management.model.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.tire.model.dot.TireDotResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.minsoo.co.tireerpserver.management.entity.QBrand.brand;
import static com.minsoo.co.tireerpserver.management.entity.QPattern.pattern;
import static com.minsoo.co.tireerpserver.management.entity.QVendor.vendor;
import static com.minsoo.co.tireerpserver.purchase.entity.QPurchase.purchase;
import static com.minsoo.co.tireerpserver.purchase.entity.QPurchaseContent.purchaseContent;
import static com.minsoo.co.tireerpserver.tire.entity.QTire.tire;
import static com.minsoo.co.tireerpserver.tire.entity.QTireDot.tireDot;

@Repository
@RequiredArgsConstructor
public class PurchaseQueryRepositoryImpl implements PurchaseQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PurchaseResponse> findPurchases(LocalDate from, LocalDate to) {
        List<PurchaseResponse> purchaseResponses =
                queryFactory.select(Projections.fields(PurchaseResponse.class,
                        purchase.id.as("purchaseId"),
                        Projections.fields(VendorResponse.class,
                                vendor.id.as("vendorId"),
                                vendor.name,
                                vendor.description,
                                Projections.constructor(BusinessInfoDTO.class, vendor.businessInfo).as("businessInfo"))
                                .as("vendor"),
                        purchase.status,
                        purchase.transactionDate))
                        .from(purchase)
                        .join(purchase.vendor, vendor)
                        .where(fromDate(from), toDate(to))
                        .fetch();

        List<PurchaseContentResponse> contentResponses = findContents(purchaseResponses.stream()
                .map(PurchaseResponse::getPurchaseId)
                .collect(Collectors.toList()));

        Map<Long, List<PurchaseContentResponse>> contentMap = contentResponses.stream()
                .collect(Collectors.groupingBy(PurchaseContentResponse::getPurchaseId));

        purchaseResponses
                .forEach(purchaseResponse -> purchaseResponse.setContents(contentMap.get(purchaseResponse.getPurchaseId())));

        return purchaseResponses;
    }

    private List<PurchaseContentResponse> findContents(List<Long> purchaseIds) {
        return queryFactory.select(Projections.fields(PurchaseContentResponse.class,
                purchaseContent.id.as("purchaseContentId"),
                purchaseContent.purchase.id.as("purchaseId"),
                Projections.fields(TireDotResponse.class,
                        tireDot.id.as("tireDotId"),
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
                        tireDot.dot,
                        tireDot.retailPrice).as("tireDot"),
                purchaseContent.price,
                purchaseContent.quantity))
                .from(purchaseContent)
                .join(purchaseContent.tireDot, tireDot)
                .join(tireDot.tire, tire)
                .join(tire.pattern, pattern)
                .join(pattern.brand, brand)
                .where(purchaseContent.purchase.id.in(purchaseIds))
                .fetch();
    }

    private Predicate fromDate(LocalDate from) {
        return from == null ? null : purchase.transactionDate.after(from.minusDays(1));
    }

    private Predicate toDate(LocalDate to) {
        return to == null ? null : purchase.transactionDate.before(to.plusDays(1));
    }
}
