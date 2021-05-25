package com.minsoo.co.tireerpserver.model.dto.management.pattern;

import com.minsoo.co.tireerpserver.model.code.PatternOption;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatternRequest {

    private String name;

    private String carType;

    private String rank;

    private String season;

    // TODO: 풀어서 요청 받기
    private List<PatternOption> options;
}
