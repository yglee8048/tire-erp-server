package com.minsoo.co.tireerpserver.model.entity.entities.management;

import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

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
@Table(name = "pattern", uniqueConstraints = {@UniqueConstraint(name = "pattern_unique_name", columnNames = {"brand_id", "name"})})
public class Pattern {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "pattern_id", nullable = false, length = 20)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "car_type")
    private String carType;

    @Column(name = "rank")
    private String rank;

    @Column(name = "season")
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
        Pattern pattern = new Pattern(patternRequest, brand);
        // options
        if (!CollectionUtils.isEmpty(patternRequest.getOptions())) {
            patternRequest.getOptions()
                    .forEach(patternOption -> pattern.getOptions().add(PatternOptions.of(pattern, patternOption)));
        }

        return pattern;
    }

    public Pattern update(PatternRequest patternRequest, Brand brand) {
        this.brand = brand;
        this.name = patternRequest.getName();
        this.carType = patternRequest.getCarType();
        this.rank = patternRequest.getRank();
        this.season = patternRequest.getSeason();

        // options
        this.options.clear();
        if (!CollectionUtils.isEmpty(patternRequest.getOptions())) {
            patternRequest.getOptions()
                    .forEach(patternOption -> this.getOptions().add(PatternOptions.of(this, patternOption)));
        }

        return this;
    }
}