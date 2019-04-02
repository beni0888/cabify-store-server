package com.jbenitoc.infrastructure.rest;

import com.jbenitoc.application.store.add.AddItemToCart;
import com.jbenitoc.application.store.add.AddItemToCartCommand;
import com.jbenitoc.application.store.create.CreateCart;
import com.jbenitoc.application.store.delete.DeleteCart;
import com.jbenitoc.application.store.delete.DeleteCartCommand;
import com.jbenitoc.domain.store.Cart;
import com.jbenitoc.infrastructure.rest.dto.AddItemRequest;
import com.jbenitoc.infrastructure.rest.dto.CreateCartResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public final class ApiController {

    private CreateCart createCart;
    private AddItemToCart addItemToCart;
    private DeleteCart deleteCart;

    @PostMapping(path = "/cart")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCartResponse createCart() {
        Cart cart = createCart.execute();
        return CreateCartResponse.fromCart(cart);
    }

    @PostMapping(path = "/cart/{cartId}/item")
    @ResponseStatus(HttpStatus.CREATED)
    public void addItemToCart(@PathVariable String cartId, @RequestBody AddItemRequest addItemRequest) {
        AddItemToCartCommand command = new AddItemToCartCommand(cartId, addItemRequest.itemCode, addItemRequest.quantity);
        addItemToCart.execute(command);
    }

    @DeleteMapping(path = "/cart/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCart(@PathVariable String cartId) {
        deleteCart.execute(new DeleteCartCommand(cartId));
    }

}
