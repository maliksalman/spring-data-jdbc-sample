package com.samples.springdatajdbc.orders;

import lombok.Builder;
import lombok.Singular;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record Order(
        UUID orderId,
        UUID customerId,
        int totalAmount,
        int tax,
        @Singular List<Item> items,
        OffsetDateTime updated,
        OffsetDateTime created
) {

    @Builder(toBuilder = true)
    public record Item(
            UUID orderId,
            UUID itemId,
            UUID productId,
            int quantity,
            int price,
            OffsetDateTime created
    ) {
    }
}

