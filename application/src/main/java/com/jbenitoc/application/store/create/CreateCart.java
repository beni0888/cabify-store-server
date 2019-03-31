package com.jbenitoc.application.store.create;

import com.jbenitoc.domain.store.Cart;
import com.jbenitoc.domain.store.CartId;
import com.jbenitoc.domain.store.CartRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateCart {

    private CartRepository cartRepository;

    public Cart execute() {
        Cart cart = Cart.create(CartId.create());
        cartRepository.save(cart);
        return cart;
    }
}
