package com.minsoo.co.tireerpserver.model.dto.tire.tire;

import com.minsoo.co.tireerpserver.model.dto.management.pattern.PatternSimpleResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TireSimpleResponse {

    private Long tireId;

    private Boolean onSale;

    private String productId;

    private PatternSimpleResponse pattern;

    private String size;

    private Integer loadIndex;

    private String speedIndex;
}
