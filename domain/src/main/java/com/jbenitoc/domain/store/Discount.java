package com.jbenitoc.domain.store;

public interface Discount {

    boolean isApplicable(CartEntry entry);

    ItemPrice getAmount(CartEntry entry, ItemPrice itemPrice);
}
