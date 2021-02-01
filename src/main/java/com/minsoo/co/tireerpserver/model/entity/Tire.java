package com.minsoo.co.tireerpserver.model.entity;

import com.minsoo.co.tireerpserver.model.code.TireOption;
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

    @Enumerated(STRING)
    @Column(name = "option")
    private TireOption option;

    @Column(name = "oe")
    private String oe;

    @OneToMany(mappedBy = "tire", fetch = LAZY)
    private List<TireDot> tireDots = new ArrayList<>();
}
