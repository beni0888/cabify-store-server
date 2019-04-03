package com.jbenitoc.domain.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceCalculatorTest {

    private static final Item ITEM_1 = Item.create(ItemCode.create("ITEM-1"), ItemName.create("ITEM 1"), ItemPrice.create(BigDecimal.valueOf(5)));
    private static final Item ITEM_2 = Item.create(ItemCode.create("ITEM-2"), ItemName.create("ITEM 2"), ItemPrice.create(BigDecimal.valueOf(10.5)));

    private PriceCalculator priceCalculator;
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        priceCalculator = new PriceCalculator(itemRepository);

        setUpItemRepositoryMock();
    }

    private void setUpItemRepositoryMock() {
        when(itemRepository.findByCode(ITEM_1.getCode())).thenReturn(Optional.of(ITEM_1));
        when(itemRepository.findByCode(ITEM_2.getCode())).thenReturn(Optional.of(ITEM_2));
    }

    @Test
    void givenACart_whenCalculateTotalAmount_thenItReturnsTheTotalAmountForTheCart() {
        Cart cart = mock(Cart.class);
        when(cart.getEntries()).thenReturn(someEntries(anEntry(ITEM_1, 1), anEntry(ITEM_2, 2)));

        CartTotalAmount total = priceCalculator.calculateTotalAmount(cart);

        assertThat(total).isEqualTo(CartTotalAmount.create(BigDecimal.valueOf(26)));
    }

    @Test
    void givenAnEmptyCart_whenCalculateTotalAmount_thenItReturnsZero() {
        Cart cart = mock(Cart.class);
        when(cart.getEntries()).thenReturn(emptyMap());

        CartTotalAmount total = priceCalculator.calculateTotalAmount(cart);

        assertThat(total).isEqualTo(CartTotalAmount.create(BigDecimal.ZERO));
    }

    private CartEntry anEntry(Item item, int quantity) {
        return CartEntry.create(item.getCode(), ItemQuantity.create(quantity));
    }

    private Map<ItemCode, CartEntry> someEntries(CartEntry... entries) {
        Map<ItemCode, CartEntry> entryMap = new HashMap<>();
        Arrays.stream(entries).forEach(entry -> entryMap.put(entry.getItemCode(), entry));
        return entryMap;
    }
}