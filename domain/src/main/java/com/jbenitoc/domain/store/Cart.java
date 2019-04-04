package com.jbenitoc.domain.store;

import lombok.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RequiredArgsConstructor(staticName = "create")
@EqualsAndHashCode
public class Cart {
    @Getter
    private final CartId id;
    private final Map<ItemCode, CartEntry> entries = new ConcurrentHashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void addEntry(Item item, ItemQuantity quantity) {
        lock.writeLock().lock();
        try {
            Optional<CartEntry> entry = getEntry(item.getCode());
            ItemQuantity totalQuantity = entry.map(cartEntry -> quantity.sum(cartEntry.getQuantity())).orElse(quantity);
            entries.put(item.getCode(), CartEntry.create(item.getCode(), totalQuantity));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Optional<CartEntry> getEntry(@NonNull ItemCode code) {
        lock.readLock().lock();
        try {
            return Optional.ofNullable(entries.get(code));
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<CartEntry> getEntries() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(entries.values());
        } finally {
            lock.readLock().unlock();
        }
    }

}

