package com.minsoo.co.tireerpserver.management.entity;

import com.minsoo.co.tireerpserver.management.model.brand.BrandRequest;
import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "brand", uniqueConstraints = {@UniqueConstraint(name = "brand_unique_name", columnNames = {"name"})})
public class Brand {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "brand_id", length = 20)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "brand", fetch = LAZY)
    private final Set<Pattern> patterns = new HashSet<>();

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
