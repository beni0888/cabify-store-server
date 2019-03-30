package com.jbenitoc.domain.store;

public final class ItemQuantityIsNotValid extends IllegalArgumentException {

    public ItemQuantityIsNotValid() {
        super("Item quantity is not valid, it should be a positive integer");
    }
}
