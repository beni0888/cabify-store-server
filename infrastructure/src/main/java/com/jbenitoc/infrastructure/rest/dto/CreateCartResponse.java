package com.jbenitoc.infrastructure.rest.dto;

import com.jbenitoc.domain.store.Cart;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class CreateCartResponse {
    public final String id;

    public static CreateCartResponse fromCart(Cart cart) {
        return new CreateCartResponse(cart.getId().getValue().toString());
    }
}
