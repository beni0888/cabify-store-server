package com.jbenitoc.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbenitoc.application.store.add.AddItemToCart;
import com.jbenitoc.application.store.add.AddItemToCartCommand;
import com.jbenitoc.application.store.create.CreateCart;
import com.jbenitoc.application.store.delete.DeleteCart;
import com.jbenitoc.application.store.delete.DeleteCartCommand;
import com.jbenitoc.application.store.total.GetCartTotalAmount;
import com.jbenitoc.application.store.total.GetCartTotalAmountQuery;
import com.jbenitoc.domain.store.*;
import com.jbenitoc.infrastructure.rest.dto.AddItemRequest;
import com.jbenitoc.infrastructure.rest.dto.GetCartTotalAmountResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @MockBean
    private GetCartTotalAmount getCartTotalAmount;

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
                .andExpect(jsonPath("$.message", containsString("There is no cart with given ID")));
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
                .andExpect(jsonPath("$.message", containsString("There is no item with given code")));
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
                .andExpect(jsonPath("$.message", containsString("There is no cart with given ID")));
    }

    @Test
    void giveCartId_whenGetTotalAmount_thenItReturnsTheTotalAmountForTheCart() throws Exception {
        String cartId = aCartId();
        CartTotalAmount total = CartTotalAmount.create(BigDecimal.TEN);
        GetCartTotalAmountQuery query = new GetCartTotalAmountQuery(cartId);

        when(getCartTotalAmount.execute(query)).thenReturn(total);

        MvcResult result = mockMvc.perform(get("/cart/{id}", cartId))
                .andExpect(status().isOk())
                .andReturn();

        GetCartTotalAmountResponse expectedResponse = new GetCartTotalAmountResponse(cartId, total.getValue());
        assertThat(result.getResponse().getContentAsString()).isEqualTo(jsonMapper.writeValueAsString(expectedResponse));
    }

    @Test
    void giveNonExistentCartId_whenGetTotalAmount_thenAErrorIsRaisedAndProperResponseIsReturned() throws Exception {
        String nonExistentCartId = aCartId();
        GetCartTotalAmountQuery query = new GetCartTotalAmountQuery(nonExistentCartId);

        doThrow(new CartDoesNotExist(CartId.create(nonExistentCartId))).when(getCartTotalAmount).execute(query);

        mockMvc.perform(get("/cart/{id}", nonExistentCartId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.message", containsString("There is no cart with given ID")));
    }

    @Test
    void giveNonInvalidCartId_whenGetTotalAmount_thenAErrorIsRaisedAndProperResponseIsReturned() throws Exception {
        String invalidCartId = aCartId();
        GetCartTotalAmountQuery query = new GetCartTotalAmountQuery(invalidCartId);

        doThrow(new CartIdIsNotValid(invalidCartId)).when(getCartTotalAmount).execute(query);

        mockMvc.perform(get("/cart/{id}", invalidCartId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is(String.format("Cart ID [%s] is not valid, it should be a valid UUID", invalidCartId))));
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