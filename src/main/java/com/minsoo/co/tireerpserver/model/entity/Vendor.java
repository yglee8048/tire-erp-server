package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorRequest;
import com.minsoo.co.tireerpserver.model.entity.embedded.Address;
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

    @Column(name = "business_number")
    private String businessNumber;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "business_type")
    private String businessType;

    @Embedded
    private Address address;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "representative")
    private String representative;

    @Column(name = "representative_phone_number")
    private String representativePhoneNumber;

    @Column(name = "manager")
    private String manager;

    @Column(name = "manager_phone_number")
    private String managerPhoneNumber;

    //== Business ==//
    private Vendor(VendorRequest createRequest) {
        this.name = createRequest.getName();
        this.businessNumber = createRequest.getBusinessNumber();
        this.businessName = createRequest.getBusinessName();
        this.businessType = createRequest.getBusinessType();
        this.address = Address.of(createRequest.getCity(), createRequest.getStreetAddress(), createRequest.getDetailAddress(), createRequest.getZipCode());
        this.fax = createRequest.getFax();
        this.emailAddress = createRequest.getEmailAddress();
        this.representative = createRequest.getRepresentative();
        this.representativePhoneNumber = createRequest.getRepresentativePhoneNumber();
        this.manager = createRequest.getManager();
        this.managerPhoneNumber = createRequest.getManagerPhoneNumber();
    }

    public static Vendor of(VendorRequest createRequest) {
        return new Vendor(createRequest);
    }

    public void update(VendorRequest updateRequest) {
        this.name = updateRequest.getName();
        this.businessNumber = updateRequest.getBusinessNumber();
        this.businessName = updateRequest.getBusinessName();
        this.businessType = updateRequest.getBusinessType();
        this.address = Address.of(updateRequest.getCity(), updateRequest.getStreetAddress(), updateRequest.getDetailAddress(), updateRequest.getZipCode());
        this.fax = updateRequest.getFax();
        this.emailAddress = updateRequest.getEmailAddress();
        this.representative = updateRequest.getRepresentative();
        this.representativePhoneNumber = updateRequest.getRepresentativePhoneNumber();
        this.manager = updateRequest.getManager();
        this.managerPhoneNumber = updateRequest.getManagerPhoneNumber();
    }
}
