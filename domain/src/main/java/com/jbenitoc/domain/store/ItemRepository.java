package com.jbenitoc.domain.store;

import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findByCode(ItemCode itemCode);

    void save(Item item);
}
