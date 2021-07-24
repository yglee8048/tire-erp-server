package com.minsoo.co.tireerpserver.cart.model;

import com.minsoo.co.tireerpserver.cart.entity.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartResponse {

    private Long cartId;

    private Long clientId;

    private Long tireDotId;

    private Long quantity;

    public CartResponse(Cart cart) {
        this.cartId = cart.getId();
        this.clientId = cart.getClient().getId();
        this.tireDotId = cart.getTireDot().getId();
        this.quantity = cart.getQuantity();
    }
}
