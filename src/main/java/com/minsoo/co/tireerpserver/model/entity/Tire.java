package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.management.tire.TireCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.management.tire.TireUpdateRequest;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire", uniqueConstraints = {@UniqueConstraint(name = "tire_product_id_unique", columnNames = {"product_id"})})
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

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "flatness_ratio", nullable = false)
    private Integer flatnessRatio;

    @Column(name = "inch", nullable = false)
    private Integer inch;

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
    private boolean runFlat;

    @Enumerated(STRING)
    @Column(name = "option")
    private TireOption option;

    @Column(name = "oe")
    private String oe;

    @OneToMany(mappedBy = "tire", fetch = LAZY)
    private List<TireDot> tireDots = new ArrayList<>();

    private Tire(TireCreateRequest createRequest, Brand brand) {
        this.brand = brand;
        this.productId = createRequest.getProductId();
        this.label = createRequest.getLabel();
        this.width = createRequest.getWidth();
        this.flatnessRatio = createRequest.getFlatnessRatio();
        this.inch = createRequest.getInch();
        this.pattern = createRequest.getPattern();
        this.loadIndex = createRequest.getLoadIndex();
        this.speedIndex = createRequest.getSpeedIndex();
        this.season = createRequest.getSeason();
        this.price = createRequest.getPrice();
        this.runFlat = createRequest.isRunFlat();
        this.option = createRequest.getOption();
        this.oe = createRequest.getOe();
    }

    public static Tire of(TireCreateRequest createRequest, Brand brand) {
        return new Tire(createRequest, brand);
    }

    public void update(TireUpdateRequest updateRequest, Brand brand) {
        this.brand = brand;
        this.productId = updateRequest.getProductId();
        this.label = updateRequest.getLabel();
        this.width = updateRequest.getWidth();
        this.flatnessRatio = updateRequest.getFlatnessRatio();
        this.inch = updateRequest.getInch();
        this.pattern = updateRequest.getPattern();
        this.loadIndex = updateRequest.getLoadIndex();
        this.speedIndex = updateRequest.getSpeedIndex();
        this.season = updateRequest.getSeason();
        this.price = updateRequest.getPrice();
        this.runFlat = updateRequest.isRunFlat();
        this.option = updateRequest.getOption();
        this.oe = updateRequest.getOe();
    }
}
