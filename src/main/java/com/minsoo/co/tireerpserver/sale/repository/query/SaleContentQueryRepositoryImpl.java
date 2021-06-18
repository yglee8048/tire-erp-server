package com.minsoo.co.tireerpserver.sale.repository.query;

import com.minsoo.co.tireerpserver.sale.model.SaleContentGridResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.minsoo.co.tireerpserver.management.entity.QBrand.brand;
import static com.minsoo.co.tireerpserver.management.entity.QPattern.pattern;
import static com.minsoo.co.tireerpserver.sale.entity.QSale.sale;
import static com.minsoo.co.tireerpserver.sale.entity.QSaleContent.saleContent;
import static com.minsoo.co.tireerpserver.tire.entity.QTire.tire;
import static com.minsoo.co.tireerpserver.tire.entity.QTireDot.tireDot;
import static com.minsoo.co.tireerpserver.user.entity.QClientCompany.clientCompany;

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
                        sale.clientCompany.id.as("clientCompanyId"),
                        sale.delivery.id.as("deliveryId"),
                        tireDot.id.as("tireDotId"),
                        tire.id.as("tireId"),
                        pattern.id.as("patternId"),
                        brand.id.as("brandId"),
                        sale.clientCompany.name.as("clientCompanyName"),
                        sale.transactionDate,
                        sale.confirmedDate,
                        sale.desiredDeliveryDate,
                        sale.status.as("saleStatus"),
                        brand.name.as("brandName"),
                        pattern.name.as("patternName"),
                        tire.tireIdentification,
                        tire.size.as("tireSize"),
                        tireDot.dot,
                        saleContent.quantity.as("saleContentQuantity"),
                        saleContent.price.as("saleContentPrice")
                ))
                .from(saleContent)
                .join(saleContent.sale, sale)
                .join(sale.clientCompany, clientCompany)
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
