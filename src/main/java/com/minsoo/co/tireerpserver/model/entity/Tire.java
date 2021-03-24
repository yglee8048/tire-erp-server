package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.tire.TireRequest;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire", uniqueConstraints = {@UniqueConstraint(name = "tire_unique_product_id", columnNames = {"product_id"})})
public class Tire {

    @Id
    @Column(name = "tire_id", length = 20)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "label")
    private String label;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "pattern", nullable = false)
    private String pattern;

    @Column(name = "load_index")
    private Integer loadIndex;

    @Column(name = "speed_index")
    private String speedIndex;

    @Column(name = "season")
    private String season;

    @Column(name = "price")
    private Integer price;

    @Column(name = "run_flat")
    private Boolean runFlat;

    @Enumerated(STRING)
    @Column(name = "option")
    private TireOption option;

    @Column(name = "oe")
    private String oe;

    @OneToMany(mappedBy = "tire", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private Set<TireDot> tireDots = new HashSet<>();

    @OneToMany(mappedBy = "tire", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private Set<TireMemo> tireMemos = new HashSet<>();

    //== Business ==//
    private Tire(TireRequest createRequest, Brand brand) {
        this.brand = brand;
        this.productId = createRequest.getProductId();
        this.label = createRequest.getLabel();
        this.size = createRequest.getSize();
        this.pattern = createRequest.getPattern();
        this.loadIndex = createRequest.getLoadIndex();
        this.speedIndex = createRequest.getSpeedIndex();
        this.season = createRequest.getSeason();
        this.price = createRequest.getPrice();
        this.runFlat = createRequest.getRunFlat();
        this.option = createRequest.getOption();
        this.oe = createRequest.getOe();
    }

    public static Tire of(TireRequest createRequest, Brand brand) {
        return new Tire(createRequest, brand);
    }

    public void update(TireRequest updateRequest, Brand brand) {
        this.brand = brand;
        this.productId = updateRequest.getProductId();
        this.label = updateRequest.getLabel();
        this.size = updateRequest.getSize();
        this.pattern = updateRequest.getPattern();
        this.loadIndex = updateRequest.getLoadIndex();
        this.speedIndex = updateRequest.getSpeedIndex();
        this.season = updateRequest.getSeason();
        this.price = updateRequest.getPrice();
        this.runFlat = updateRequest.getRunFlat();
        this.option = updateRequest.getOption();
        this.oe = updateRequest.getOe();
    }
}
