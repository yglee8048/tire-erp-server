package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.dto.vendor.VendorCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.vendor.VendorUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.audit.BaseEntity;
import com.minsoo.co.tireerpserver.model.entity.embedded.AdminHistory;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "vendor", uniqueConstraints = {@UniqueConstraint(name = "vendor_name_unique", columnNames = {"name"})})
public class Vendor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id", length = 20)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private AdminHistory history;

    private Vendor(VendorCreateRequest createRequest, Admin operator) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
        this.history = new AdminHistory(operator);
    }

    public static Vendor create(VendorCreateRequest createRequest, Admin operator) {
        return new Vendor(createRequest, operator);
    }

    public void update(VendorUpdateRequest updateRequest, Admin operator) {
        this.name = updateRequest.getName();
        this.description = updateRequest.getDescription();
        this.history = new AdminHistory(operator);
    }
}
