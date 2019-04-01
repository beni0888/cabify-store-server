package com.jbenitoc.infrastructure.initialization;

import com.jbenitoc.domain.store.*;
import com.jbenitoc.infrastructure.configuration.ProductsConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    ItemRepository itemRepository;
    ProductsConfiguration productsConfiguration;

    public DataLoader(ItemRepository itemRepository, ProductsConfiguration productsConfiguration) {
        this.itemRepository = itemRepository;
        this.productsConfiguration = productsConfiguration;
    }

    @Override
    public void run(String... args) throws Exception {

        productsConfiguration.getProducts().forEach(product -> {
            Item item = Item.create(ItemCode.create(product.getCode()), ItemName.create(product.getName()), ItemPrice.create(product.getPrice()));
            itemRepository.save(item);
        });
    }
}
