package com.minsoo.co.tireerpserver.model.dto.management.pattern;

import com.minsoo.co.tireerpserver.model.code.PatternOption;
import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Pattern;
import com.minsoo.co.tireerpserver.model.entity.entities.management.PatternOptions;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PatternResponse {

    private Long patternId;

    private BrandResponse brand;

    private String name;

    private String carType;

    private String rank;

    private String season;

    private List<PatternOption> options;

    public PatternResponse(Pattern pattern) {
        this.patternId = pattern.getId();
        this.brand = BrandResponse.of(pattern.getBrand());
        this.name = pattern.getName();
        this.carType = pattern.getCarType();
        this.rank = pattern.getRank();
        this.season = pattern.getSeason();
        this.options = pattern.getOptions().stream().map(PatternOptions::getOption).collect(Collectors.toList());
    }

    public static PatternResponse of(Pattern pattern) {
        return new PatternResponse(pattern);
    }
}