package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.tire.TireRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
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
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tire_id", nullable = false, length = 20)
    private Long id;

    @Column(name = "product_id", nullable = false, columnDefinition = "타이어 ID")
    private String productId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_pattern_id", nullable = false)
    private TirePattern pattern;

    @Column(name = "width", nullable = false, columnDefinition = "단면폭")
    private Integer width;

    @Column(name = "flatness_ratio", nullable = false, columnDefinition = "편평비")
    private Integer flatnessRatio;

    @Column(name = "inch", nullable = false, columnDefinition = "인치")
    private Integer inch;

    @Column(name = "size", nullable = false, columnDefinition = "사이즈(단면폭 + '/' + 편평비 + 'R' + 인치)")
    private String size;

    @Column(name = "load_index", columnDefinition = "하중 지수")
    private Integer loadIndex;

    @Column(name = "speed_index", columnDefinition = "속도 지수")
    private String speedIndex;

    @Column(name = "season", columnDefinition = "계절")
    private String season;

    @Column(name = "price", columnDefinition = "소비자 금액")
    private Integer price;

    @Column(name = "run_flat")
    private Boolean runFlat;

    @Column(name = "label")
    private String label;

    @Enumerated(STRING)
    @Column(name = "option")
    private TireOption option;

    @Column(name = "oe")
    private String oe;

    @OneToMany(mappedBy = "tire", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<TireDot> tireDots = new HashSet<>();

    @OneToMany(mappedBy = "tire", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<TireMemo> tireMemos = new HashSet<>();

    //== Business ==//
    private Tire(TireRequest createRequest, Brand brand) {
        this.brand = brand;
        this.productId = createRequest.getProductId();
        this.label = createRequest.getLabel();
        this.width = createRequest.getWidth();
        this.flatnessRatio = createRequest.getFlatnessRatio();
        this.inch = createRequest.getInch();
        this.size = "" + createRequest.getWidth() + createRequest.getFlatnessRatio() + createRequest.getInch();
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
        this.width = updateRequest.getWidth();
        this.flatnessRatio = updateRequest.getFlatnessRatio();
        this.inch = updateRequest.getInch();
        this.size = "" + updateRequest.getWidth() + updateRequest.getFlatnessRatio() + updateRequest.getInch();
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
