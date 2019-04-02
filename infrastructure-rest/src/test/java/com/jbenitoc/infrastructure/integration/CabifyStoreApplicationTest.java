package com.jbenitoc.infrastructure.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbenitoc.domain.store.Cart;
import com.jbenitoc.domain.store.CartId;
import com.jbenitoc.domain.store.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CabifyStoreApplicationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jsonMapper;
    @Autowired
    private CartRepository cartRepository;

    @Test
    void whenCreateCart_thenItWorks() throws Exception {
        MvcResult result = mockMvc.perform(post("/cart")).andExpect(status().isCreated()).andReturn();
        String response = result.getResponse().getContentAsString();
        String cartId = jsonMapper.readTree(response).get("id").asText();

        assertThat(cartId).isNotEmpty();
        assertThat(cartRepository.findById(CartId.create(cartId))).isNotEmpty();
    }

    @Test
    void whenDeleteCart_thenItWorks() throws Exception {
        Cart cart = Cart.create(CartId.create());
        cartRepository.save(cart);

        mockMvc.perform(delete("/cart/{id}", cart.getId()))
                .andExpect(status().isNoContent());

        assertThat(cartRepository.findById(cart.getId())).isEmpty();
    }
}
