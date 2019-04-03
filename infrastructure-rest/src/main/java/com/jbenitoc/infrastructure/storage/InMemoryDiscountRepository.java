package com.jbenitoc.infrastructure.storage;

import com.jbenitoc.domain.store.Discount;
import com.jbenitoc.domain.store.DiscountRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class InMemoryDiscountRepository implements DiscountRepository {

    private Set<Discount> discounts = new HashSet<>();

    @Override
    public List<Discount> findAll() {
        return new ArrayList<>(discounts);
    }

    @Override
    public void save(Discount discount) {
        discounts.add(discount);
    }
}
