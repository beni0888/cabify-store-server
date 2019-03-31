package com.jbenitoc.infrastructure;

import com.jbenitoc.application.store.create.CreateCart;
import com.jbenitoc.domain.store.CartRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfiguration {

    @Bean
    public CreateCart createCart(CartRepository repository) {
        return new CreateCart(repository);
    }
}
