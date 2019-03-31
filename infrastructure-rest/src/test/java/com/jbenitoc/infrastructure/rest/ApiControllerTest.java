package com.jbenitoc.infrastructure.rest;

import com.jbenitoc.application.store.create.CreateCart;
import com.jbenitoc.domain.store.Cart;
import com.jbenitoc.domain.store.CartId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
class ApiControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CreateCart createCart;

    @Test
    void givenCreateCartRequest_whenExecuted_thenACartIsCreatedAndReturned() throws Exception {
        Cart createdCart = aCart();
        when(createCart.execute()).thenReturn(createdCart);

        mockMvc.perform(post("/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdCart.getId().getValue().toString())));
    }

    private Cart aCart() {
        return Cart.create(CartId.create());
    }
}