package com.minsoo.co.tireerpserver.model.response.stock;

import com.minsoo.co.tireerpserver.model.response.management.WarehouseResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StockGridResponse {

    private Long stockId;
    private String nickname;

    private Long tireDotId;
    private WarehouseResponse warehouse;

    private Integer quantity;
    private Boolean lock;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;
}
