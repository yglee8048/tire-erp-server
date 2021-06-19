package com.minsoo.co.tireerpserver.purchase.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PurchaseStatus {

    REQUESTED("매입요청"),
    CONFIRMED("매입확정");

    private final String description;
}
