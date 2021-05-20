package com.minsoo.co.tireerpserver.model.dto.tire.pattern;

import com.minsoo.co.tireerpserver.model.code.PatternOption;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatternRequest {

    private Long brandId;

    private String name;

    private String carType;

    private String rank;

    private String season;

    private List<PatternOption> options;
}
