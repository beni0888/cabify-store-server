package com.jbenitoc.infrastructure.storage;

import com.jbenitoc.domain.store.BuyTwoGetOneFree;
import com.jbenitoc.domain.store.Discount;
import com.jbenitoc.domain.store.DiscountRepository;
import com.jbenitoc.domain.store.ItemCode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class InMemoryDiscountRepositoryTest {

    private static final ItemCode ITEM_CODE_1 = ItemCode.create("ITEM-1");
    private static final ItemCode ITEM_CODE_2 = ItemCode.create("ITEM-2");

    private DiscountRepository repository = new InMemoryDiscountRepository();

    @Test
    void givenADiscount_whenAdd_thenItIsAddedToTheRepository() {
        List<Discount> discounts = asList(new BuyTwoGetOneFree(ITEM_CODE_1), new BuyTwoGetOneFree(ITEM_CODE_2));

        discounts.forEach(repository::save);

        assertThat(repository.findAll()).hasSize(discounts.size());
    }

    @Test
    void whenSameDiscountIsAddedMultipleTimes_thenItIsStoredJustOnce() {
        List<Discount> discounts = asList(new BuyTwoGetOneFree(ITEM_CODE_1), new BuyTwoGetOneFree(ITEM_CODE_1),
                new BuyTwoGetOneFree(ITEM_CODE_2));

        discounts.forEach(repository::save);

        assertThat(repository.findAll()).hasSize(2);
    }


}