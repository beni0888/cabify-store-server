package com.jbenitoc.domain.store;

public class CartDoesNotExist extends RuntimeException {

    public CartDoesNotExist(CartId cartId) {
        super(String.format("There is no cart with given ID [%s]", cartId));
    }
}
