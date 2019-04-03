package com.jbenitoc.application.store.add;

import com.jbenitoc.domain.store.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AddItemToCartTest {
    private static final CartId CART_ID = CartId.create();
    private static final ItemCode ITEM_CODE = ItemCode.create("TSHIRT");

    private AddItemToCart addItemToCart;
    private CartRepository cartRepository;
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        itemRepository = mock(ItemRepository.class);
        addItemToCart = new AddItemToCart(cartRepository, itemRepository);
    }

    @Test
    void givenCartIdAndItemCodeAndQuantity_whenExecute_thenGivenQuantityOfItemsAreAddedToTheCart() {
        Cart cart = aCartMock();
        Item item = anItem(ITEM_CODE);
        ItemQuantity quantity = ItemQuantity.create(1);

        when(cartRepository.findById(CART_ID)).thenReturn(Optional.of(cart));
        when(itemRepository.findByCode(ITEM_CODE)).thenReturn(Optional.of(anItem(ITEM_CODE)));

        AddItemToCartCommand command = new AddItemToCartCommand(CART_ID.toString(), ITEM_CODE.toString(), quantity.getValue());
        addItemToCart.execute(command);

        verify(cart, times(1)).addEntry(item, quantity);
    }


    @Test
    void givenNonExistentCart_whenExecute_thenThrowsItThrowsAnException() {
        CartId nonExistentCartId = CartId.create();

        Exception e = assertThrows(CartDoesNotExist.class, () -> {
            when(cartRepository.findById(nonExistentCartId)).thenReturn(Optional.empty());

            AddItemToCartCommand command = new AddItemToCartCommand(nonExistentCartId.toString(), ITEM_CODE.toString(), 1);
            addItemToCart.execute(command);
        });

        assertThat(e.getMessage()).isEqualTo(String.format("There is no cart with given ID [%s]", nonExistentCartId));
    }

    @Test
    void givenNonExistentItem_whenExecute_thenThrowsItThrowsAnException() {
        ItemCode nonExistentItemCode = ItemCode.create("FOO");

        Exception e = assertThrows(ItemDoesNotExist.class, () -> {
            when(cartRepository.findById(CART_ID)).thenReturn(Optional.of(aCartMock()));
            when(itemRepository.findByCode(nonExistentItemCode)).thenReturn(Optional.empty());

            AddItemToCartCommand command = new AddItemToCartCommand(CART_ID.toString(), nonExistentItemCode.toString(), 1);
            addItemToCart.execute(command);
        });

        assertThat(e.getMessage()).isEqualTo(String.format("There is no item with given code [%s]", nonExistentItemCode));
    }

    private Item anItem(ItemCode code) {
        return Item.create(code, ItemName.create("Whatever"), Price.create(BigDecimal.ONE));
    }

    private Cart aCartMock() {
        return mock(Cart.class);
    }
}