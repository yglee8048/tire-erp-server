package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorRequest;
import com.minsoo.co.tireerpserver.model.entity.embedded.Address;
import com.minsoo.co.tireerpserver.model.entity.embedded.BusinessInfo;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "vendor", uniqueConstraints = {@UniqueConstraint(name = "vendor_name_unique", columnNames = {"name"})})
public class Vendor {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "vendor_id", length = 20, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private BusinessInfo businessInfo;

    //== Business ==//
    private Vendor(VendorRequest createRequest) {
        this.name = createRequest.getName();
        this.businessInfo = BusinessInfo.builder()
                .businessNumber(createRequest.getBusinessNumber())
                .businessName(createRequest.getBusinessName())
                .businessType(createRequest.getBusinessType())
                .address(Address.of(createRequest.getCity(), createRequest.getStreetAddress(), createRequest.getDetailAddress(), createRequest.getZipCode()))
                .fax(createRequest.getFax())
                .emailAddress(createRequest.getEmailAddress())
                .representative(createRequest.getRepresentative())
                .representativePhoneNumber(createRequest.getRepresentativePhoneNumber())
                .manager(createRequest.getManager())
                .managerPhoneNumber(createRequest.getManagerPhoneNumber())
                .build();
    }

    public static Vendor of(VendorRequest createRequest) {
        return new Vendor(createRequest);
    }

    public void update(VendorRequest updateRequest) {
        this.name = updateRequest.getName();
        this.businessInfo = BusinessInfo.builder()
                .businessNumber(updateRequest.getBusinessNumber())
                .businessName(updateRequest.getBusinessName())
                .businessType(updateRequest.getBusinessType())
                .address(Address.of(updateRequest.getCity(), updateRequest.getStreetAddress(), updateRequest.getDetailAddress(), updateRequest.getZipCode()))
                .fax(updateRequest.getFax())
                .emailAddress(updateRequest.getEmailAddress())
                .representative(updateRequest.getRepresentative())
                .representativePhoneNumber(updateRequest.getRepresentativePhoneNumber())
                .manager(updateRequest.getManager())
                .managerPhoneNumber(updateRequest.getManagerPhoneNumber())
                .build();
    }
}
