package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandRequest;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "brand", uniqueConstraints = {@UniqueConstraint(name = "brand_name_unique", columnNames = {"name"})})
public class Brand {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "brand_id", length = 20)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    //== Business ==//
    private Brand(BrandRequest createRequest) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
    }

    public static Brand of(BrandRequest createRequest) {
        return new Brand(createRequest);
    }

    public void update(BrandRequest updateRequest) {
        this.name = updateRequest.getName();
        this.description = updateRequest.getDescription();
    }
}
