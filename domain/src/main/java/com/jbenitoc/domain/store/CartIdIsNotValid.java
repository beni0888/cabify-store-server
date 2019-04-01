package com.jbenitoc.domain.store;

public final class CartIdIsNotValid extends IllegalArgumentException {

    public CartIdIsNotValid(String id) {
        super(String.format("Cart ID [%s] is not valid, it should be a valid UUID", id));
    }
}
