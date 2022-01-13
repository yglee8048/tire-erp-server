package com.minsoo.co.tireerpserver.repository.pruchase.query;

import com.minsoo.co.tireerpserver.model.response.purchase.PurchaseContentGridResponse;
import com.minsoo.co.tireerpserver.repository.JPAQuerySnippet;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.minsoo.co.tireerpserver.entity.management.QBrand.brand;
import static com.minsoo.co.tireerpserver.entity.management.QPattern.pattern;
import static com.minsoo.co.tireerpserver.entity.management.QVendor.vendor;
import static com.minsoo.co.tireerpserver.entity.management.QWarehouse.warehouse;
import static com.minsoo.co.tireerpserver.entity.purchase.QPurchase.purchase;
import static com.minsoo.co.tireerpserver.entity.purchase.QPurchaseContent.purchaseContent;
import static com.minsoo.co.tireerpserver.entity.stock.QStock.stock;
import static com.minsoo.co.tireerpserver.entity.tire.QTire.tire;
import static com.minsoo.co.tireerpserver.entity.tire.QTireDot.tireDot;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PurchaseContentQueryRepositoryImpl implements PurchaseContentQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PurchaseContentGridResponse> findPurchaseContentGrids(LocalDate from, LocalDate to) {
        return selectPurchaseContentGridsQuery()
                .where(from == null || to == null ? null : purchase.transactionDate.between(from, to))
                .fetch();
    }

    @Override
    public List<PurchaseContentGridResponse> findPurchaseContentGridsByPurchaseId(Long purchaseId) {
        return selectPurchaseContentGridsQuery()
                .where(purchase.id.eq(purchaseId))
                .fetch();
    }

    private JPAQuery<PurchaseContentGridResponse> selectPurchaseContentGridsQuery() {
        return queryFactory
                .select(JPAQuerySnippet.purchaseContentGridResponse(purchase, purchaseContent, tire, tireDot, pattern, brand, stock, warehouse, vendor))
                .from(purchaseContent)
                .join(purchase).on(purchaseContent.purchase.eq(purchase))
                .join(vendor).on(purchase.vendor.eq(vendor))
                .join(tireDot).on(purchaseContent.tireDot.eq(tireDot))
                .join(stock).on(purchaseContent.stock.eq(stock))
                .join(warehouse).on(stock.warehouse.eq(warehouse))
                .join(tire).on(tireDot.tire.eq(tire))
                .join(pattern).on(tire.pattern.eq(pattern))
                .join(brand).on(pattern.brand.eq(brand));
    }
}
