package com.minsoo.co.tireerpserver.services.sale.repository.query;

import com.minsoo.co.tireerpserver.api.v1.model.dto.sale.SaleContentGridResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.minsoo.co.tireerpserver.services.account.entity.QCustomer.customer;
import static com.minsoo.co.tireerpserver.services.management.entity.QBrand.brand;
import static com.minsoo.co.tireerpserver.services.management.entity.QPattern.pattern;
import static com.minsoo.co.tireerpserver.services.sale.entity.QSale.sale;
import static com.minsoo.co.tireerpserver.services.sale.entity.QSaleContent.saleContent;
import static com.minsoo.co.tireerpserver.services.tire.entity.QTire.tire;
import static com.minsoo.co.tireerpserver.services.tire.entity.QTireDot.tireDot;

@Repository
@RequiredArgsConstructor
public class SaleContentQueryRepositoryImpl implements SaleContentQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SaleContentGridResponse> findSaleContents(LocalDate from, LocalDate to) {
        return queryFactory
                .select(Projections.fields(SaleContentGridResponse.class,
                        saleContent.id.as("saleContentId"),
                        sale.id.as("saleId"),
                        customer.id.as("customerId"),
                        sale.delivery.id.as("deliveryId"),
                        tireDot.id.as("tireDotId"),
                        tire.id.as("tireId"),
                        pattern.id.as("patternId"),
                        brand.id.as("brandId"),
                        customer.name.as("customerName"),
                        sale.transactionDate,
                        sale.confirmedDate,
                        sale.desiredDeliveryDate,
                        sale.status.as("saleStatus"),
                        brand.name.as("brandName"),
                        pattern.name.as("patternName"),
                        tire.size.as("tireSize"),
                        tireDot.dot,
                        saleContent.quantity.as("saleContentQuantity"),
                        saleContent.price.as("saleContentPrice")
                ))
                .from(saleContent)
                .join(saleContent.sale, sale)
                .join(sale.customer, customer)
                .join(saleContent.tireDot, tireDot)
                .join(tireDot.tire, tire)
                .join(tire.pattern, pattern)
                .join(pattern.brand, brand)
                .where(fromDate(from), toDate(to))
                .fetch();
    }

    private Predicate fromDate(LocalDate from) {
        return from == null ? null : sale.transactionDate.after(from.minusDays(1));
    }

    private Predicate toDate(LocalDate to) {
        return to == null ? null : sale.transactionDate.before(to.plusDays(1));
    }
}
