package com.jbenitoc.domain.store;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public final class CartEntry {
    private final Item item;
    private final ItemQuantity quantity;
}
