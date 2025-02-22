package com.jbenitoc.infrastructure.rest;

import com.jbenitoc.application.store.add.AddItemToCart;
import com.jbenitoc.application.store.add.AddItemToCartCommand;
import com.jbenitoc.application.store.create.CreateCart;
import com.jbenitoc.application.store.delete.DeleteCart;
import com.jbenitoc.application.store.delete.DeleteCartCommand;
import com.jbenitoc.application.store.total.GetCartTotalAmount;
import com.jbenitoc.application.store.total.GetCartTotalAmountQuery;
import com.jbenitoc.domain.store.Cart;
import com.jbenitoc.domain.store.Price;
import com.jbenitoc.infrastructure.logging.Loggable;
import com.jbenitoc.infrastructure.rest.dto.AddItemRequest;
import com.jbenitoc.infrastructure.rest.dto.CreateCartResponse;
import com.jbenitoc.infrastructure.rest.dto.GetCartTotalAmountResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
public class StoreController {

    private CreateCart createCart;
    private AddItemToCart addItemToCart;
    private DeleteCart deleteCart;
    private GetCartTotalAmount getCartTotalAmount;

    @PostMapping(path = "/cart")
    @ResponseStatus(HttpStatus.CREATED)
    @Loggable(operation = "Create Cart")
    public CreateCartResponse createCart() {
        Cart cart = createCart.execute();
        log.info("Cart created { cart_id: {} }", cart.getId());
        return CreateCartResponse.fromCart(cart);
    }

    @PostMapping(path = "/cart/{cartId}/item")
    @ResponseStatus(HttpStatus.CREATED)
    @Loggable(operation = "Add Item To Cart")
    public void addItemToCart(@PathVariable String cartId, @RequestBody AddItemRequest addItemRequest) {
        AddItemToCartCommand command = new AddItemToCartCommand(cartId, addItemRequest.itemCode, addItemRequest.quantity);
        addItemToCart.execute(command);
        log.info("Item added to cart { item_code: {}, quantity: {}, cart_id: {} }", addItemRequest.itemCode,
                addItemRequest.quantity, cartId);
    }

    @DeleteMapping(path = "/cart/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Loggable(operation = "Delete Cart")
    public void deleteCart(@PathVariable String cartId) {
        deleteCart.execute(new DeleteCartCommand(cartId));
        log.info("Cart deleted { cart_id: {} }", cartId);
    }

    @GetMapping(path = "/cart/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Loggable(operation = "Get Total Amount For Cart")
    public GetCartTotalAmountResponse getCartTotalAmount(@PathVariable String cartId) {
        GetCartTotalAmountQuery query = new GetCartTotalAmountQuery(cartId);
        Price totalAmount = getCartTotalAmount.execute(query);
        log.info("Got total amount for cart { cart_id: {}, total_amount: {}}", cartId, totalAmount);
        return new GetCartTotalAmountResponse(cartId, totalAmount.getValue());
    }

}
