package com.minsoo.co.tireerpserver.model.dto.management.pattern;

import com.minsoo.co.tireerpserver.model.dto.management.brand.BrandResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatternSimpleResponse {

    private BrandResponse brand;

    private String name;
}
