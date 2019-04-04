package com.jbenitoc.domain.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceCalculatorTest {

    private static final Item ITEM_1 = Item.create(ItemCode.create("ITEM-1"), ItemName.create("ITEM 1"), Price.create(BigDecimal.valueOf(5)));
    private static final Item ITEM_2 = Item.create(ItemCode.create("ITEM-2"), ItemName.create("ITEM 2"), Price.create(BigDecimal.valueOf(10.5)));

    private PriceCalculator priceCalculator;
    private ItemRepository itemRepository;
    private DiscountRepository discountRepository;

    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        discountRepository = mock(DiscountRepository.class);
        priceCalculator = new PriceCalculator(itemRepository, discountRepository);

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

        Price total = priceCalculator.calculateTotalAmount(cart);

        assertThat(total).isEqualTo(Price.create(BigDecimal.valueOf(26)));
    }

    @Test
    void givenAnEmptyCart_whenCalculateTotalAmount_thenItReturnsZero() {
        Cart cart = mock(Cart.class);
        when(cart.getEntries()).thenReturn(emptyList());

        Price total = priceCalculator.calculateTotalAmount(cart);

        assertThat(total).isEqualTo(Price.create(BigDecimal.ZERO));
    }

    @Test
    void givenACartWithApplicableDiscounts_whenCalculateTotalAmount_thenDiscountsAreAppliedAndItReturnsTheCorrespondingTotalAmount() {
        Cart cart = mock(Cart.class);

        when(discountRepository.findAll()).thenReturn(asList(
                new BuyTwoGetOneFree(ITEM_1.getCode()),
                new BulkPurchaseDiscount(ITEM_2.getCode(), ItemQuantity.create(3), Price.create(BigDecimal.ONE))
        ));
        when(cart.getEntries()).thenReturn(someEntries(
                anEntry(ITEM_1, 2),
                anEntry(ITEM_2, 3)
        ));

        Price total = priceCalculator.calculateTotalAmount(cart);

        // Price should be
        // - ITEM_1: 5 (BuyTwoGetOneFree)
        // - ITEM_2: 3 (BulkPurchaseDiscount - price: 1 per unit)
        // - TOTAL:  8
        assertThat(total).isEqualTo(Price.create(BigDecimal.valueOf(8)));
    }

    private CartEntry anEntry(Item item, int quantity) {
        return CartEntry.create(item.getCode(), ItemQuantity.create(quantity));
    }

    private List<CartEntry> someEntries(CartEntry... entries) {
        return asList(entries);
    }

}