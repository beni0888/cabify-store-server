package com.jbenitoc.domain;

import lombok.*;

@EqualsAndHashCode
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ValueObject<T> {
    @NonNull protected final T value;

    @Override
    public String toString() {
        return value.toString();
    }
}
