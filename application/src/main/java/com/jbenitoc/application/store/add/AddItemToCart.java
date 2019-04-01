package com.jbenitoc.application.store.add;

import com.jbenitoc.domain.store.*;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AddItemToCart {

    private CartRepository cartRepository;
    private ItemRepository itemRepository;

    public void execute(AddItemToCartCommand command) {
        CartId cartId = CartId.create(command.cartId);
        ItemCode itemCode = ItemCode.create(command.itemCode);
        ItemQuantity quantity = ItemQuantity.create(command.quantity);

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartDoesNotExist(cartId));
        Item item = itemRepository.findByCode(itemCode).orElseThrow(() -> new ItemDoesNotExist(itemCode));

        cart.addEntry(item, quantity);
    }
}
