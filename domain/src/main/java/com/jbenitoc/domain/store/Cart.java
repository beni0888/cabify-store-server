package com.jbenitoc.domain.store;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor(staticName = "create")
@Getter
@EqualsAndHashCode
public final class Cart {
    private final CartId id;
    private final Map<ItemCode, CartEntry> entries = new ConcurrentHashMap<>();
}
