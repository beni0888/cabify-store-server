package com.jbenitoc.domain.store;

public final class ItemCodeIsNotValid extends IllegalArgumentException {

    public ItemCodeIsNotValid() {
        super("ItemCode is not valid, it should be a non-empty string");
    }
}
