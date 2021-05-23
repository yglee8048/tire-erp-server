package com.minsoo.co.tireerpserver.model.dto.stock;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyStockRequest {

    private Long stockId;

    private String nickname;

    private Long warehouseId;

    private Long quantity;

    private Boolean lock;
}
