package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import com.minsoo.co.tireerpserver.model.entity.embedded.AdminHistory;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tire")
public class Tire {

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
}
