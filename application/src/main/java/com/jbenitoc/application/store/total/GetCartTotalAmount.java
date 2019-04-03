package com.jbenitoc.application.store.total;

import com.jbenitoc.domain.store.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetCartTotalAmount {

    private CartRepository cartRepository;
    private PriceCalculator priceCalculator;

    public Price execute(GetCartTotalAmountQuery query) {
        CartId cartId = CartId.create(query.cartId);
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartDoesNotExist(cartId));

        return priceCalculator.calculateTotalAmount(cart);
    }
}
