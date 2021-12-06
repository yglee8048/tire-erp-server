package com.minsoo.co.tireerpserver.entity.management;

import com.minsoo.co.tireerpserver.entity.BaseTimeEntity;
import com.minsoo.co.tireerpserver.model.request.management.BrandRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "brand")
public class Brand extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public static Brand of(BrandRequest request) {
        Brand brand = new Brand();
        return brand.update(request);
    }

    public Brand update(BrandRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        return this;
    }
}
