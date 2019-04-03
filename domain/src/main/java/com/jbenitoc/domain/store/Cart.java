package com.jbenitoc.domain.store;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor(staticName = "create")
@Getter
@EqualsAndHashCode
public class Cart {
    private final CartId id;
    private final Map<ItemCode, CartEntry> entries = new ConcurrentHashMap<>();

    public synchronized void addEntry(Item item, ItemQuantity quantity) {
        Optional<CartEntry> entry = getEntry(item.getCode());
        ItemQuantity totalQuantity = entry.map(cartEntry -> quantity.sum(cartEntry.getQuantity())).orElse(quantity);
        entries.put(item.getCode(), CartEntry.create(item.getCode(), totalQuantity));
    }

    public Optional<CartEntry> getEntry(@NonNull ItemCode code) {
        return Optional.ofNullable(entries.get(code));
    }

}
