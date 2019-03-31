package com.jbenitoc.application.store.add;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public final class AddItemToCartCommand {
    public final String cartId;
    public final String itemCode;
    public final int quantity;
}
