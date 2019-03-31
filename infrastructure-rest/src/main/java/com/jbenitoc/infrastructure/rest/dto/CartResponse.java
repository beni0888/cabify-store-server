package com.jbenitoc.infrastructure.rest.dto;

import com.jbenitoc.domain.store.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class CartResponse {
    private final String id;

    public static CartResponse fromCart(Cart cart) {
        return new CartResponse(cart.getId().getValue().toString());
    }
}
