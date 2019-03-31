package com.jbenitoc.infrastructure.rest;

import com.jbenitoc.application.store.add.AddItemToCart;
import com.jbenitoc.application.store.add.AddItemToCartCommand;
import com.jbenitoc.application.store.create.CreateCart;
import com.jbenitoc.domain.store.Cart;
import com.jbenitoc.infrastructure.rest.dto.AddItemRequest;
import com.jbenitoc.infrastructure.rest.dto.CartResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public final class ApiController {

    private CreateCart createCart;
    private AddItemToCart addItemToCart;

    @PostMapping(path = "/cart")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse createCart() {
        Cart cart = createCart.execute();
        return CartResponse.fromCart(cart);
    }

    @PostMapping(path = "/cart/{cartId}/item")
    @ResponseStatus(HttpStatus.CREATED)
    public void addItemToCart(@PathVariable String cartId, @RequestBody AddItemRequest addItemRequest) {
        AddItemToCartCommand command = new AddItemToCartCommand(cartId, addItemRequest.itemCode, addItemRequest.quantity);
        addItemToCart.execute(command);
    }

}
