package com.jbenitoc.model;

import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ValueObject<T> {
    @NonNull protected final T value;
}
