package com.jbenitoc.domain.store;

public final class CartIdIsNotValid extends IllegalArgumentException {

    public CartIdIsNotValid() {
        super("Cart ID is not valid, it cannot be null");
    }
}
