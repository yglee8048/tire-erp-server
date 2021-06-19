package com.minsoo.co.tireerpserver.sale.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SaleStatus {

    REQUESTED("매출요청"),
    CANCELED("매출취소"),
    CONFIRMED("매출확정"),
    SHIPPED("배송완료");

    private final String description;
}
