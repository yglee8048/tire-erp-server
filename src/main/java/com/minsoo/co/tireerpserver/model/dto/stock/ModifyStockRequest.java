package com.minsoo.co.tireerpserver.model.dto.stock;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyStockRequest {

    private Long stockId;

    private Long warehouseId;

    private String nickname;

    private Long quantity;

    private Boolean lock;
}
