package com.jbenitoc.infrastructure.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbenitoc.domain.store.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static com.jbenitoc.domain.store.ItemQuantity.ONE;
import static java.util.Arrays.asList;
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
        "cabify.store.products[1].price=20.00",
        "cabify.store.products[2].code=MUG",
        "cabify.store.products[2].name=Cabify Cofee Mug",
        "cabify.store.products[2].price=7.50"
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

    @ParameterizedTest
    @MethodSource("providerWithCabifyExpectations")
    void whenGetCartTotalAmount_thenItWorks(List<String> itemCodes, Price expectedTotal) throws Exception {
        Cart cart = Cart.create(CartId.create());
        cartRepository.save(cart);

        itemCodes.forEach(itemCode -> {
            cart.addEntry(itemRepository.findByCode(ItemCode.create(itemCode)).orElseThrow(RuntimeException::new), ONE);
        });

        MvcResult result = mockMvc.perform(get("/cart/{id}", cart.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId", is(cart.getId().toString())))
                .andReturn();

        Price obtainedTotal = Price.create(BigDecimal
                .valueOf(jsonMapper.readTree(result.getResponse().getContentAsString()).get("totalAmount").asDouble()));
        assertThat(obtainedTotal).isEqualTo(expectedTotal);
    }

    private static Stream providerWithCabifyExpectations() {
        return Stream.of(
                Arguments.of(asList("VOUCHER", "TSHIRT", "MUG"), price(32.50)),
                Arguments.of(asList("VOUCHER", "TSHIRT", "VOUCHER"), price(25.00)),
                Arguments.of(asList("TSHIRT", "TSHIRT", "TSHIRT", "VOUCHER", "TSHIRT"), price(81.00)),
                Arguments.of(asList("VOUCHER", "TSHIRT", "VOUCHER", "VOUCHER", "MUG", "TSHIRT", "TSHIRT"), price(74.50))
        );
    }

    private static Price price(double value) {
        return Price.create(BigDecimal.valueOf(value));
    }
}
