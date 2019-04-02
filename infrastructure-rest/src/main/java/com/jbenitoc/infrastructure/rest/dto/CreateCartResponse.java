package com.jbenitoc.infrastructure.rest.dto;

import com.jbenitoc.domain.store.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class CreateCartResponse {
    private final String id;

    public static CreateCartResponse fromCart(Cart cart) {
        return new CreateCartResponse(cart.getId().getValue().toString());
    }
}
