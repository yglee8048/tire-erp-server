package com.minsoo.co.tireerpserver.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {

    @NotNull(message = "client_id 는 필수 값입니다.")
    private Long clientId;

    @NotNull(message = "tire_dot_id 는 필수 값입니다.")
    private Long tireDotId;

    @NotNull(message = "수량은 필수 값입니다.")
    private Long quantity;
}
