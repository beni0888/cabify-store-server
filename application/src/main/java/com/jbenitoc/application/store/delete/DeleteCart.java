package com.jbenitoc.application.store.delete;

import com.jbenitoc.domain.store.Cart;
import com.jbenitoc.domain.store.CartDoesNotExist;
import com.jbenitoc.domain.store.CartId;
import com.jbenitoc.domain.store.CartRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteCart {

    private CartRepository cartRepository;

    public void execute(DeleteCartCommand command) {
        CartId cartId = CartId.create(command.id);
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartDoesNotExist(cartId));
        cartRepository.remove(cart);
    }
}
