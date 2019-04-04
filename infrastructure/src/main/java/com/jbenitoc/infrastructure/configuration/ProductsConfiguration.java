package com.jbenitoc.infrastructure.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("cabify.store")
@Getter
@Setter
@ToString
public class ProductsConfiguration {
    private List<Item> products = new ArrayList();

    @Getter
    @Setter
    @ToString
    static public class Item {
        private String code;
        private String name;
        private BigDecimal price;
    }
}
