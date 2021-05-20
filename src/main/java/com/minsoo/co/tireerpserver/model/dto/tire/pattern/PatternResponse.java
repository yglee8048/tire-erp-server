package com.minsoo.co.tireerpserver.model.dto.tire.pattern;

import com.minsoo.co.tireerpserver.model.code.PatternOption;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PatternResponse {

    private Long tirePatternId;

    private BrandResponse brand;

    private String name;

    private String carType;

    private String rank;

    private String season;

    private List<PatternOption> options;

    public PatternResponse(Pattern pattern) {
        this.tirePatternId = pattern.getId();
        this.brand = BrandResponse.of(pattern.getBrand());
        this.name = pattern.getName();
        this.carType = pattern.getCarType();
        this.rank = pattern.getRank();
        this.season = pattern.getSeason();
        this.options = pattern.getOptions();
    }

    public static PatternResponse of(Pattern pattern) {
        return new PatternResponse(pattern);
    }
}