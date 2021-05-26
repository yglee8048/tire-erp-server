package com.minsoo.co.tireerpserver.repository.query;

import com.minsoo.co.tireerpserver.model.dto.general.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.model.dto.purchase.PurchaseResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.minsoo.co.tireerpserver.model.entity.entities.management.QVendor.vendor;
import static com.minsoo.co.tireerpserver.model.entity.entities.purchase.QPurchase.purchase;

@Repository
@RequiredArgsConstructor
public class PurchaseQueryRepositoryImpl implements PurchaseQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PurchaseResponse> findPurchases(LocalDate from, LocalDate to) {
        queryFactory.select(Projections.fields(PurchaseResponse.class,
                purchase.id.as("purchaseId"),
                Projections.fields(VendorResponse.class,
                        vendor.id.as("vendorId"),
                        vendor.name,
                        vendor.description,
                        Projections.fields(BusinessInfoDTO.class,
                                vendor.businessInfo.businessNumber,
                                vendor.businessInfo.businessName
                        ).as("businessInfo")
                ).as("vendor")
        ))
                .from(purchase)
                .join(purchase.vendor, vendor)
                .where()
                .fetch();
        return null;
    }
}
