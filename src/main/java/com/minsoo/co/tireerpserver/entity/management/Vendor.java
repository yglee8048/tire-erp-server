package com.minsoo.co.tireerpserver.entity.management;

import com.minsoo.co.tireerpserver.entity.BaseTimeEntity;
import com.minsoo.co.tireerpserver.entity.BusinessInfo;
import com.minsoo.co.tireerpserver.model.request.management.VendorRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "vendor")
public class Vendor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private BusinessInfo businessInfo;

    public static Vendor of(VendorRequest request) {
        Vendor vendor = new Vendor();
        return vendor.update(request);
    }

    public Vendor update(VendorRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.businessInfo = new BusinessInfo(request.getBusinessInfo());
        return this;
    }
}
