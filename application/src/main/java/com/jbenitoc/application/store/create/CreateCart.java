package com.jbenitoc.application.store.create;

import com.jbenitoc.domain.store.Cart;
import com.jbenitoc.domain.store.CartId;
import com.jbenitoc.domain.store.CartRepository;

public final class CreateCart {

    CartRepository cartRepository;

    public CreateCart(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart execute() {
        Cart cart = Cart.create(CartId.create());
        cartRepository.save(cart);
        return cart;
    }
}
