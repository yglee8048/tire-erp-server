package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.management.vendor.VendorUpdateRequest;
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
    @Column(name = "vendor_id", length = 20)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    private Vendor(VendorCreateRequest createRequest) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
    }

    public static Vendor of(VendorCreateRequest createRequest) {
        return new Vendor(createRequest);
    }

    public void update(VendorUpdateRequest updateRequest) {
        this.name = updateRequest.getName();
        this.description = updateRequest.getDescription();
    }
}
