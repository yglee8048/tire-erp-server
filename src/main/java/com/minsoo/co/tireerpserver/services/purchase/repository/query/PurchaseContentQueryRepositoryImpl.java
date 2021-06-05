package com.minsoo.co.tireerpserver.services.purchase.repository.query;

import com.minsoo.co.tireerpserver.api.v1.model.dto.general.BusinessInfoDTO;
import com.minsoo.co.tireerpserver.api.v1.model.dto.management.vendor.VendorResponse;
import com.minsoo.co.tireerpserver.api.v1.model.dto.purchase.PurchaseSimpleResponse;
import com.minsoo.co.tireerpserver.api.v1.model.dto.purchase.content.PurchaseContentResponse;
import com.minsoo.co.tireerpserver.api.v1.model.dto.tire.dot.TireDotResponse;
import com.minsoo.co.tireerpserver.services.management.entity.QVendor;
import com.minsoo.co.tireerpserver.services.purchase.entity.QPurchase;
import com.minsoo.co.tireerpserver.services.stock.entity.QStock;
import com.minsoo.co.tireerpserver.services.tire.entity.QTireDot;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.minsoo.co.tireerpserver.services.management.entity.QVendor.vendor;
import static com.minsoo.co.tireerpserver.services.purchase.entity.QPurchase.purchase;
import static com.minsoo.co.tireerpserver.services.purchase.entity.QPurchaseContent.purchaseContent;
import static com.minsoo.co.tireerpserver.services.stock.entity.QStock.stock;
import static com.minsoo.co.tireerpserver.services.tire.entity.QTireDot.tireDot;

@Repository
@RequiredArgsConstructor
public class PurchaseContentQueryRepositoryImpl implements PurchaseContentQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PurchaseContentResponse> findPurchaseContents(LocalDate from, LocalDate to) {
        return queryFactory.select(Projections.fields(PurchaseContentResponse.class,
                purchaseContent.id.as("purchaseContentId"),
                purchaseContent.price,
                purchaseContent.quantity,
                Projections.fields(PurchaseSimpleResponse.class,
                        purchase.id.as("purchaseId"),
                        Projections.fields(VendorResponse.class,
                                vendor.id.as("vendorId"),
                                vendor.name,
                                vendor.description,
                                Projections.constructor(BusinessInfoDTO.class, vendor.businessInfo).as("businessInfo")
                        ).as("vendor"),
                        purchase.status,
                        purchase.purchaseDate).as("purchase"),
                Projections.fields(TireDotResponse.class,
                        tireDot.id.as("tireDotId"),
                        tireDot.dot,
                        tireDot.retailPrice,
                        stock.quantity.sum().as("totalQuantity")).as("tireDot")))
                .from(purchaseContent)
                .join(purchaseContent.purchase, purchase)
                .join(purchase.vendor, vendor)
                .join(purchaseContent.tireDot, tireDot)
                .leftJoin(tireDot.stocks, stock)
                .fetch();
    }
}
