package com.minsoo.co.tireerpserver.model.response.tire.query;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TireDotPriceResponse {

    private Long tireDotId;
    private Long tireId;
    private String dot;

    private Long price;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;
}
