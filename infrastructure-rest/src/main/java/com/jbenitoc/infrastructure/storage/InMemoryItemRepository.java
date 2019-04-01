package com.jbenitoc.infrastructure.storage;

import com.jbenitoc.domain.store.Item;
import com.jbenitoc.domain.store.ItemCode;
import com.jbenitoc.domain.store.ItemRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryItemRepository implements ItemRepository {

    private Map<ItemCode, Item> items = new ConcurrentHashMap<>();

    @Override
    public Optional<Item> findByCode(ItemCode itemCode) {
        return Optional.ofNullable(items.get(itemCode));
    }

    @Override
    public void save(Item item) {
        items.put(item.getCode(), item);
    }
}
