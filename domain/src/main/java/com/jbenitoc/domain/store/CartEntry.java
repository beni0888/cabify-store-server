package com.jbenitoc.domain.store;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(staticName = "create")
@Getter
@EqualsAndHashCode
@ToString
public final class CartEntry {
    private final ItemCode itemCode;
    private final ItemQuantity quantity;
}
