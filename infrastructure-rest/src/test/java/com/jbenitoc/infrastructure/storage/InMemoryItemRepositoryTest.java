package com.jbenitoc.infrastructure.storage;

import com.jbenitoc.domain.store.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryItemRepositoryTest {

    private ItemRepository itemRepository = new InMemoryItemRepository();

    @Test
    void givenAnItem_whenSave_thenItIsStoredInTheRepository() {
        Item item = Item.create(ItemCode.create("TSHIRT"), ItemName.create("Cabify T-Shirt"), ItemPrice.create(BigDecimal.ONE));

        itemRepository.save(item);
        Optional<Item> retrievedItem = itemRepository.findByCode(item.getCode());

        assertThat(retrievedItem).isNotEmpty();
        assertThat(retrievedItem.get()).isEqualTo(item);
    }
}