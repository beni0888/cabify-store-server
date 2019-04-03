package com.jbenitoc.domain.store;

import java.util.List;

public interface DiscountRepository {

    List<Discount> findAll();

    void save(Discount discount);
}
