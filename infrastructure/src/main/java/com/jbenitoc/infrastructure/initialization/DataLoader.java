package com.jbenitoc.infrastructure.initialization;

import com.jbenitoc.domain.store.*;
import com.jbenitoc.infrastructure.configuration.ProductsConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private ItemRepository itemRepository;
    private ProductsConfiguration productsConfiguration;
    private DiscountRepository discountRepository;

    @Override
    public void run(String... args) throws Exception {
        loadProducts();
        loadDiscounts();
    }

    private void loadDiscounts() {
        discountRepository.save(new BuyTwoGetOneFree(ItemCode.create("VOUCHER")));
        discountRepository.save(new BulkPurchaseDiscount(ItemCode.create("TSHIRT"), ItemQuantity.create(3), Price.create(BigDecimal.valueOf(19.00))));
    }

    private void loadProducts() {
        productsConfiguration.getProducts().forEach(product -> {
            Item item = Item.create(ItemCode.create(product.getCode()), ItemName.create(product.getName()), Price.create(product.getPrice()));
            itemRepository.save(item);
        });
    }
}
