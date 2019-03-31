package com.jbenitoc.infrastructure;

import com.jbenitoc.application.store.add.AddItemToCart;
import com.jbenitoc.application.store.create.CreateCart;
import com.jbenitoc.domain.store.CartRepository;
import com.jbenitoc.domain.store.ItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfiguration {

    @Bean
    public CreateCart createCart(CartRepository repository) {
        return new CreateCart(repository);
    }

    @Bean
    public AddItemToCart addItemToCart(CartRepository cartRepository, ItemRepository itemRepository) {
        return new AddItemToCart(cartRepository, itemRepository);
    }
}
