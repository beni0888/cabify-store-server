package com.jbenitoc.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbenitoc.application.store.add.AddItemToCart;
import com.jbenitoc.application.store.add.AddItemToCartCommand;
import com.jbenitoc.application.store.create.CreateCart;
import com.jbenitoc.application.store.delete.DeleteCart;
import com.jbenitoc.application.store.delete.DeleteCartCommand;
import com.jbenitoc.domain.store.*;
import com.jbenitoc.infrastructure.rest.dto.AddItemRequest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jsonMapper;
    @MockBean
    private CreateCart createCart;
    @MockBean
    private AddItemToCart addItemToCart;
    @MockBean
    private DeleteCart deleteCart;

    @Test
    void givenCreateCartRequest_whenExecuted_thenACartIsCreatedAndReturned() throws Exception {
        Cart createdCart = aCart();
        when(createCart.execute()).thenReturn(createdCart);

        mockMvc.perform(post("/cart").accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(createdCart.getId().getValue().toString())));
    }

    @Test
    void giveCartIdAndProductDetails_whenAddToCart_thenTheProductIsAddedToTheCart() throws Exception {
        String requestPayload = jsonMapper.writeValueAsString(anAddItemRequest());

        mockMvc.perform(post("/cart/{id}/item", aCartId()).content(requestPayload).contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void givenNonExistentCartId_whenAddToCart_thenAErrorIsRaisedAndProperResponseIsReturned() throws Exception {
        AddItemRequest addItemRequest = anAddItemRequest();
        String nonExistentCartId = aCartId();
        String requestPayload = jsonMapper.writeValueAsString(addItemRequest);
        AddItemToCartCommand command = new AddItemToCartCommand(nonExistentCartId, addItemRequest.itemCode, addItemRequest.quantity);

        doThrow(new CartDoesNotExist(CartId.create(nonExistentCartId))).when(addItemToCart).execute(command);

        mockMvc.perform(post("/cart/{id}/item", nonExistentCartId).content(requestPayload).contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.message", CoreMatchers.containsString("There is no cart with given ID")));
    }

    @Test
    void givenNonExistentProduct_whenAddToCart_thenAErrorIsRaisedAndProperResponseIsReturned() throws Exception {
        AddItemRequest addItemRequest = anAddItemRequestWithNonExistentItem();
        String cartId = aCartId();
        String requestPayload = jsonMapper.writeValueAsString(addItemRequest);
        AddItemToCartCommand command = new AddItemToCartCommand(cartId, addItemRequest.itemCode, addItemRequest.quantity);

        doThrow(new ItemDoesNotExist(ItemCode.create(addItemRequest.itemCode))).when(addItemToCart).execute(command);

        mockMvc.perform(post("/cart/{id}/item", cartId).content(requestPayload).contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.message", CoreMatchers.containsString("There is no item with given code")));
    }

    @Test
    void givenCartId_whenDeleteCart_thenTheAssociatedCartIsRemovedFromRepository() throws Exception {
        mockMvc.perform(delete("/cart/{id}", aCartId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenNonExistentCartId_whenDeleteCart_thenAErrorIsRaisedAndProperResponseIsReturned() throws Exception {
        String nonExistentCartId = aCartId();
        DeleteCartCommand command = new DeleteCartCommand(nonExistentCartId);

        doThrow(new CartDoesNotExist(CartId.create(nonExistentCartId))).when(deleteCart).execute(command);

        mockMvc.perform(delete("/cart/{id}", nonExistentCartId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.message", CoreMatchers.containsString("There is no cart with given ID")));
    }

    private AddItemRequest anAddItemRequestWithNonExistentItem() {
        return new AddItemRequest("WRONG", 1);
    }

    private AddItemRequest anAddItemRequest() {
        return new AddItemRequest("TSHIRT", 1);
    }

    private Cart aCart() {
        return Cart.create(CartId.create());
    }

    private String aCartId() {
        return CartId.create().getValue().toString();
    }
}