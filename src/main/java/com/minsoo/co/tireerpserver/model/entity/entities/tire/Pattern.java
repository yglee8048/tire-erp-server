package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.model.dto.tire.pattern.PatternRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "pattern", uniqueConstraints = {@UniqueConstraint(name = "pattern_unique_and_name", columnNames = {"brand_id", "name"})})
public class Pattern {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "pattern_id", nullable = false, length = 20)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "brand_id", nullable = false, columnDefinition = "제조사 ID")
    private Brand brand;

    @Column(name = "name", nullable = false, columnDefinition = "패턴 이름")
    private String name;

    @Column(name = "car_type", columnDefinition = "차종")
    private String carType;

    @Column(name = "rank", columnDefinition = "등급")
    private String rank;

    @Column(name = "season", columnDefinition = "계절")
    private String season;

    @OneToMany(mappedBy = "pattern", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<PatternOptions> options = new HashSet<>();

    //== Business ==//
    public Pattern(PatternRequest patternRequest, Brand brand) {
        this.brand = brand;
        this.name = patternRequest.getName();
        this.carType = patternRequest.getCarType();
        this.rank = patternRequest.getRank();
        this.season = patternRequest.getSeason();
    }

    public static Pattern of(PatternRequest patternRequest, Brand brand) {
        return new Pattern(patternRequest, brand);
    }

    public Pattern update(PatternRequest patternRequest, Brand brand) {
        this.brand = brand;
        this.name = patternRequest.getName();
        this.carType = patternRequest.getCarType();
        this.rank = patternRequest.getRank();
        this.season = patternRequest.getSeason();

        return this;
    }
}
