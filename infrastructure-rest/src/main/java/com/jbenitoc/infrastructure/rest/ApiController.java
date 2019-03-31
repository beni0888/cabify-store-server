package com.jbenitoc.infrastructure.rest;

import com.jbenitoc.application.store.create.CreateCart;
import com.jbenitoc.domain.store.Cart;
import com.jbenitoc.infrastructure.rest.dto.CartResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public final class ApiController {

    private CreateCart createCart;

    @PostMapping(path = "/cart")
    public CartResponse createCart() {
        Cart cart = createCart.execute();
        return CartResponse.fromCart(cart);
    }
}
