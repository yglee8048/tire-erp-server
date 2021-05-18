package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.model.code.PatternOption;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Brand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire_pattern", uniqueConstraints = {@UniqueConstraint(name = "tire_pattern_unique_name", columnNames = {"name"})})
public class TirePattern {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tire_pattern_id", nullable = false, length = 20)
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

    @Enumerated(STRING)
    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "tire_pattern_options", joinColumns = @JoinColumn(name = "tire_pattern_id", referencedColumnName = "tire_pattern_id"))
    @Column(name = "option", nullable = false)
    private List<PatternOption> patternOptions;
}
