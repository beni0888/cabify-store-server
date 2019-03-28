package com.jbenitoc.model.store;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor(staticName = "create")
@Getter
@EqualsAndHashCode
public final class Item {
    private final ItemCode code;
    private final ItemName name;
    private final ItemPrice price;

}
