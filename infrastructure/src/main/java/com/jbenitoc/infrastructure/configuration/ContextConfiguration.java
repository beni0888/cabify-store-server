package com.jbenitoc.infrastructure.configuration;

import com.jbenitoc.application.store.add.AddItemToCart;
import com.jbenitoc.application.store.create.CreateCart;
import com.jbenitoc.application.store.delete.DeleteCart;
import com.jbenitoc.application.store.total.GetCartTotalAmount;
import com.jbenitoc.domain.store.CartRepository;
import com.jbenitoc.domain.store.DiscountRepository;
import com.jbenitoc.domain.store.ItemRepository;
import com.jbenitoc.domain.store.PriceCalculator;
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

    @Bean
    public DeleteCart deleteCart(CartRepository cartRepository) {
        return new DeleteCart(cartRepository);
    }

    @Bean
    public GetCartTotalAmount getCartTotalAmount(CartRepository cartRepository, PriceCalculator priceCalculator) {
        return new GetCartTotalAmount(cartRepository, priceCalculator);
    }

    @Bean
    public PriceCalculator priceCalculator(ItemRepository itemRepository, DiscountRepository discountRepository) {
        return new PriceCalculator(itemRepository, discountRepository);
    }
}
