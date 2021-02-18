package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorRequest;
import com.minsoo.co.tireerpserver.model.entity.embedded.BusinessInfo;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "vendor", uniqueConstraints = {@UniqueConstraint(name = "vendor_unique_name", columnNames = {"name"})})
public class Vendor {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "vendor_id", length = 20, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private BusinessInfo businessInfo;

    //== Business ==//
    private Vendor(VendorRequest createRequest) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
        this.businessInfo = BusinessInfo.of(createRequest.getBusinessInfo());
    }

    public static Vendor of(VendorRequest createRequest) {
        return new Vendor(createRequest);
    }

    public void update(VendorRequest updateRequest) {
        this.name = updateRequest.getName();
        this.description = updateRequest.getDescription();
        this.businessInfo = BusinessInfo.of(updateRequest.getBusinessInfo());
    }
}
