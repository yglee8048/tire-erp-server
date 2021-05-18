package com.minsoo.co.tireerpserver.repository.query;

import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorSimpleResponse;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseSimpleResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.minsoo.co.tireerpserver.model.entity.QBrand.*;
import static com.minsoo.co.tireerpserver.model.entity.QVendor.*;
import static com.minsoo.co.tireerpserver.model.entity.QWarehouse.*;

@Repository
@RequiredArgsConstructor
public class ManagementQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<BrandSimpleResponse> findAllBrandNames() {
        return queryFactory
                .select(Projections.fields(BrandSimpleResponse.class,
                        brand.id.as("brandId"),
                        brand.name.as("name")))
                .from(brand)
                .fetch();
    }

    public List<VendorSimpleResponse> findAllVendorNames() {
        return queryFactory
                .select(Projections.fields(VendorSimpleResponse.class,
                        vendor.id.as("vendorId"),
                        vendor.name.as("name")))
                .from(vendor)
                .fetch();
    }

    public List<WarehouseSimpleResponse> findAllWarehouseNames() {
        return queryFactory
                .select(Projections.fields(WarehouseSimpleResponse.class,
                        warehouse.id.as("warehouseId"),
                        warehouse.name.as("name")))
                .from(warehouse)
                .fetch();
    }
}
