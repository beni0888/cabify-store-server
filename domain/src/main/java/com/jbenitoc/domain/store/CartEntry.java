package com.jbenitoc.domain.store;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor(staticName = "create")
@Getter
@EqualsAndHashCode
public final class CartEntry {
    private final ItemCode itemCode;
    private final ItemQuantity quantity;
}
