package com.jbenitoc.infrastructure.integration;

import com.jbenitoc.domain.store.ItemCode;
import com.jbenitoc.domain.store.ItemRepository;
import com.jbenitoc.infrastructure.configuration.ProductsConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = {
        "cabify.store.products[0].code=VOUCHER",
        "cabify.store.products[0].name=Cabify Voucher",
        "cabify.store.products[0].price=1.50",
        "cabify.store.products[1].code=TSHIRT",
        "cabify.store.products[1].name=Cabify T-Shirt",
        "cabify.store.products[1].price=15.00"
})
class ProductInitialLoadingTest {

    @Autowired
    ProductsConfiguration productsConfiguration;
    @Autowired
    ItemRepository itemRepository;

    @Test
    void givenApplicationConfiguration_whenApplicationStarts_thenProductsAreLoaded() {
        assertThat(productsConfiguration.getProducts()).hasSize(2);
    }

    @Test
    void givenApplicationConfiguration_whenApplicationStarts_thenProductsAreSavedInItemRepository() {
        assertThat(itemRepository.findByCode(ItemCode.create("VOUCHER"))).isNotEmpty();
        assertThat(itemRepository.findByCode(ItemCode.create("TSHIRT"))).isNotEmpty();
    }
}