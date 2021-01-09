package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.dto.tire.TireCreateRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.TireUpdateRequest;
import com.minsoo.co.tireerpserver.model.entity.audit.BaseEntity;
import com.minsoo.co.tireerpserver.model.entity.embedded.AdminHistory;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tire")
public class Tire extends BaseEntity {

    @Id
    @Column(name = "tire_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "label")
    private String label;

    @Column(name = "width")
    private Integer width;

    @Column(name = "flatness_ratio")
    private Integer flatnessRatio;

    @Column(name = "inch")
    private Integer inch;

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

    @Column(name = "option")
    private TireOption option;

    @Column(name = "oe")
    private String oe;

    @OneToMany(mappedBy = "tire", fetch = FetchType.LAZY)
    private List<TireDot> tireDots = new ArrayList<>();

    @Embedded
    private AdminHistory history;

    public Tire(TireCreateRequest createRequest, Brand brand, Admin operator) {
        this.brand = brand;
        this.label = createRequest.getLabel();
        this.width = createRequest.getWidth();
        this.flatnessRatio = createRequest.getFlatnessRatio();
        this.inch = createRequest.getInch();
        this.loadIndex = createRequest.getLoadIndex();
        this.speedIndex = createRequest.getSpeedIndex();
        this.season = createRequest.getSeason();
        this.price = createRequest.getPrice();
        this.runFlat = createRequest.isRunFlat();
        this.option = createRequest.getOption();
        this.oe = createRequest.getOe();
        this.history = new AdminHistory(operator);
    }

    public static Tire create(TireCreateRequest createRequest, Brand brand, Admin operator) {
        return new Tire(createRequest, brand, operator);
    }

    public void update(TireUpdateRequest updateRequest, Brand brand, Admin operator) {
        this.brand = brand;
        this.label = updateRequest.getLabel();
        this.width = updateRequest.getWidth();
        this.flatnessRatio = updateRequest.getFlatnessRatio();
        this.inch = updateRequest.getInch();
        this.loadIndex = updateRequest.getLoadIndex();
        this.speedIndex = updateRequest.getSpeedIndex();
        this.season = updateRequest.getSeason();
        this.price = updateRequest.getPrice();
        this.runFlat = updateRequest.isRunFlat();
        this.option = updateRequest.getOption();
        this.oe = updateRequest.getOe();
        this.history = new AdminHistory(operator);
    }
}
