package com.minsoo.co.tireerpserver.model.response.tire;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TireDotGridResponse {

    private TireDotResponse tireDot;

    private Long price;

    private Integer sumOfOpenedStock;
    private Integer sumOfStock;

    private Double averageOfPurchasePrice;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;
}
