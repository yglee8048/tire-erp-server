package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.dto.brand.BrandCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.brand.BrandUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.audit.BaseEntity;
import com.minsoo.co.tireerpserver.model.entity.embedded.AdminHistory;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "brand", uniqueConstraints = {@UniqueConstraint(name = "brand_name_unique", columnNames = {"name"})})
public class Brand extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id", length = 20)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private AdminHistory history;

    private Brand(BrandCreateRequest createRequest, Admin operator) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
        this.history = new AdminHistory(operator);
    }

    public static Brand create(BrandCreateRequest createRequest, Admin operator) {
        return new Brand(createRequest, operator);
    }

    public void update(BrandUpdateRequest updateRequest, Admin operator) {
        this.name = updateRequest.getName();
        this.description = updateRequest.getDescription();
        this.history = new AdminHistory(operator);
    }
}
