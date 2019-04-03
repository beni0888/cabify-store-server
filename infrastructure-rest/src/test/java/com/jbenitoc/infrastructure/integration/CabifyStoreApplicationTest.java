package com.jbenitoc.infrastructure.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbenitoc.domain.store.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "cabify.store.products[0].code=VOUCHER",
        "cabify.store.products[0].name=Cabify Voucher",
        "cabify.store.products[0].price=5.00",
        "cabify.store.products[1].code=TSHIRT",
        "cabify.store.products[1].name=Cabify T-Shirt",
        "cabify.store.products[1].price=20.00"
})
public class CabifyStoreApplicationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jsonMapper;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemRepository itemRepository;

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

    @Test
    void whenGetCartTotalAmount_thenItWorks() throws Exception {
        Cart cart = Cart.create(CartId.create());
        cartRepository.save(cart);
        cart.addEntry(itemRepository.findByCode(ItemCode.create("VOUCHER")).orElseThrow(RuntimeException::new), ItemQuantity.create(2));
        cart.addEntry(itemRepository.findByCode(ItemCode.create("TSHIRT")).orElseThrow(RuntimeException::new), ItemQuantity.create(1));
        Price expectedTotal = Price.create(BigDecimal.valueOf(30.0));

        MvcResult result = mockMvc.perform(get("/cart/{id}", cart.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId", is(cart.getId().toString())))
                .andReturn();

        Price obtainedTotal = Price.create(BigDecimal
                .valueOf(jsonMapper.readTree(result.getResponse().getContentAsString()).get("totalAmount").asDouble()));
        assertThat(expectedTotal).isEqualTo(obtainedTotal);
    }
}
