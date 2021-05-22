package com.minsoo.co.tireerpserver.model.dto.stock;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyStockRequest {

    private Long stockId;

    private Long tireDotId;

    private String nickname;

    private Long warehouseId;

    private Long quantity;

    private boolean lock;
}
