package com.jbenitoc.domain.store;

public class ItemDoesNotExist extends RuntimeException {

    public ItemDoesNotExist(ItemCode itemCode) {
        super(String.format("There is no item with given code [%s]", itemCode));
    }
}
